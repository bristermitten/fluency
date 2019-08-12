package me.bristermitten.fluency.menu;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.Util;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.data.ButtonHolder;
import me.bristermitten.fluency.data.PageList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.bristermitten.fluency.button.distribution.ButtonDistribution.SIMPLE;

public class Menu {
    private static final int MENU_WIDTH = 9;
    private final PageList pages;
    Inventory inventory;
    private ButtonHolder[] buttons;
    private String title;
    private int size;
    private ButtonDistribution distribution;
    private ButtonHolder background;

    public Menu() {
        title = "Title";
        size = MENU_WIDTH * 6;
        pages = new PageList(this);
        distribution = SIMPLE.get();
        distribution.init(size);

        background = new ButtonHolder();
        buttons = new ButtonHolder[size];
        Arrays.fill(buttons, new ButtonHolder());
    }

    private void updateMenu() {
        updateMenu(true);
    }

    private void updateMenu(boolean updatePages) {
        inventory = Bukkit.createInventory(new MenuHolder(this), size, Util.color(title));
        for (int i = 0; i < buttons.length; i++) {
            ButtonHolder button = buttons[i];
            if (button == null) continue;
            MenuButton b = button.get();
            if (background.has()) {
                if (b == null || b.getType() == Material.AIR)
                    b = background.get();
            }
            inventory.setItem(i, b);
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

    public void background(MenuButton background) {
        this.background.set(background);
        updateMenu();
    }

    public void background(ButtonHolder background) {
        this.background = background;
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
        buttons[slot].set(button);
        updateMenu();
    }

    public void addButton(MenuButton button) {
        if (!distribution.hasNext() && isFull()) {
            addPage().addButton(button);
            return;
        }
        buttons[distribution.nextSlot()].set(button);
        updateMenu();
    }

    public void addButton(ButtonHolder button) {
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
                last.button(last.size - 1, Fluency.PAGE_NEXT);
            }
        }
        pages.add(e);
        return e;
    }

    public MenuButton button(int i) {
        return buttons[i].get();
    }

    public PageList pages() {
        return pages;
    }

    public List<MenuButton> buttons() {
        return Arrays.stream(buttons).map(ButtonHolder::get).collect(Collectors.toList());
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
