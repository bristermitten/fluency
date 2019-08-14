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
    protected ButtonHolder background;
    Inventory inventory;
    private ButtonHolder[] buttons;
    private String title;
    private int size;
    private ButtonDistribution distribution;

    public Menu() {
        title = "Title";
        size = MENU_WIDTH * 6;
        pages = new PageList(this);
        distribution = SIMPLE.get();
        distribution.init(size);

        background = new ButtonHolder();
        buttons = new ButtonHolder[size];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new ButtonHolder();
        }
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
            if (background.has() && (b == null || b.getType() == Material.AIR)) b = background.get();
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
            Menu last = pages.getLast();
            if (last.isFull()) {
                last = addPage();
            }
            last.addButton(button);
            return;
        }
        int i = distribution.nextSlot();
        ButtonHolder dis = buttons[i];
        dis.set(button);
        updateMenu();
    }

    public void addButton(ButtonHolder button) {
        if (!distribution.hasNext() && isFull()) {
            Menu last = pages.getLast();
            if (last.isFull()) {
                last = addPage();
            }
            last.addButton(button);
            return;
        }
        buttons[distribution.nextSlot()] = button;
        updateMenu();
    }

    public boolean isFull() {
        if (background.has()) {
            for (ButtonHolder button : buttons) {
                if (!button.has()) return false;
                return button.get().equals(background.get());
            }
        } else {
            for (ButtonHolder button : buttons) {
                if (!button.has()) return false;
            }
            return true;
        }
        return true;
    }

    public Page addPage() {
        Page page = new Page(this);
        page.addButton(Fluency.PAGE_PREVIOUS);
        Menu last = pages.getLast();
        if (last != null) {
            MenuButton button = last.button(last.size - 1);
            if (button != null) {
                page.addButton(button);
            }
            last.button(last.size - 1, Fluency.PAGE_NEXT);
        }
        pages.add(page);
        return page;
    }

    public MenuButton button(int i) {
        ButtonHolder button = buttons[i];
        if (!button.has() && background.has()) return background.get();
        return button.get();
    }

    public PageList pages() {
        return pages;
    }

    public List<MenuButton> buttons() {
        return Arrays.stream(buttons).map(ButtonHolder::get).collect(Collectors.toList());
    }

    public void open(Player whoClicked) {
        updateMenu();
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

