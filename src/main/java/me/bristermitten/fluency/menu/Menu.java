package me.bristermitten.fluency.menu;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.Util;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.data.PageList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

import static me.bristermitten.fluency.button.distribution.ButtonDistribution.SIMPLE;

public class Menu {
    private static final int MENU_WIDTH = 9;
    private final PageList pages;
    Inventory inventory;
    private MenuButton[] buttons;
    private String title;
    private int size;
    private ButtonDistribution distribution;

    public Menu() {
        title = "Title";
        size = MENU_WIDTH * 6;
        pages = new PageList(this);
        distribution = SIMPLE.get();
        distribution.init(size);

        buttons = new MenuButton[size];
    }

    private void updateMenu() {
        updateMenu(true);
    }

    private void updateMenu(boolean updatePages) {
        inventory = Bukkit.createInventory(new MenuHolder(this), size, Util.color(title));
        for (int i = 0; i < buttons.length; i++) {
            inventory.setItem(i, buttons[i]);
        }
        pages.forEachPage(p -> p.updateMenu(false));
    }

    public String title() {
        return title;
    }

    public void title(String title) {
        this.title = title;
        updateMenu();
    }

    public int size() {
        return size;
    }

    public void size(int size) {
        this.size = size;
        buttons = Arrays.copyOf(buttons, size);
        distribution.init(size);
        updateMenu();
    }

    public ButtonDistribution distribution() {
        return distribution;
    }

    public void distribution(ButtonDistribution distribution) {
        this.distribution = distribution;
        this.distribution.init(size);
    }

    public void button(int slot, MenuButton button) {
        buttons[slot] = button;
        updateMenu();
    }

    public void addButton(MenuButton button) {
        if (!distribution.hasNext() && isFull()) {
            addPage().addButton(button);
            return;
        }
        buttons[distribution.nextSlot()] = button;
        updateMenu();
    }

    public boolean isFull() {
        if (inventory == null) updateMenu();
        return inventory.firstEmpty() == -1;
    }

    public Page addPage() {
        Page e = new Page(this);
        e.addButton(Fluency.PAGE_PREVIOUS);
        Menu last = pages.getLast();
        if (last != null) {
            MenuButton button = last.button(last.size - 1);
            if (button != null) {
                e.addButton(button);
                last.button(last.size-1, Fluency.PAGE_NEXT);
            }
        }
        pages.add(e);
        return e;
    }

    public MenuButton button(int i) {
        return buttons[i];
    }

    public PageList pages() {
        return pages;
    }

    public void open(Player whoClicked) {
        whoClicked.openInventory(inventory);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "pages=" + pages +
                ", inventory=" + inventory +
                ", buttons=" + Arrays.toString(buttons) +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", distribution=" + distribution +
                '}';
    }
}
