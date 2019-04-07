package me.bristermitten.spigotmenus.menu;

import lombok.val;
import me.bristermitten.spigotmenus.menu.button.MenuButton;
import me.bristermitten.spigotmenus.menu.page.Page;
import me.bristermitten.spigotmenus.util.Chat;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

import static me.bristermitten.spigotmenus.util.Constants.MAX_INV_SIZE;
import static me.bristermitten.spigotmenus.util.Constants.MAX_PAGE_SIZE;

public class Menu {
    private final LinkedList<MenuButton> buttons;
    private final LinkedList<Menu> pages;
    protected Inventory inventory;


    //MENU INFO
    private String title;
    private int size;

    public Menu(String title, int size, MenuButton... buttons) {
        Objects.requireNonNull(title, "title");
        Validate.isTrue(size > 0, "size is negative");

        this.title = title;
        this.size = size;
        this.buttons = new LinkedList<>(Arrays.asList(buttons));
        this.pages = new LinkedList<>();
        this.pages.add(this);
        updateInfo();
    }

    private void updateInfo() {
        if (size > MAX_INV_SIZE) {
            int pagesAmount = pagesNeeded(size);
            for (int i = 0; i < pagesAmount; i++) {
                addPage();
            }
            size = MAX_INV_SIZE;
        }
        this.inventory = Bukkit.createInventory(inventory.getHolder(), size, Chat.color(title));
        for (int i = 0; i < buttons.size(); i++) {
            MenuButton menuButton = buttons.get(i);
            ItemStack item = menuButton == null ? null : menuButton.getItem();
            this.inventory.setItem(i, item);
        }
    }

    private void addPage() {
        int pageNumber = pages.size();
        Page page = new Page(title + "Page " + pageNumber, size);
        val lastButton = pages.getLast().buttons.getLast();
        if (lastButton != null) {
        }
        pages.add(page);
    }

    public void addButton(MenuButton button) {
        buttons.add(button);
    }

    private int pagesNeeded(int size) {
        if (size >= MAX_INV_SIZE) {
            return 1;
        }
        int pagesAmount = 0;
        while (size >= MAX_PAGE_SIZE) {
            size -= MAX_PAGE_SIZE;
            pagesAmount++;
        }
        return pagesAmount;
    }

    public MenuButton getButton(int slot) {
        return buttons.get(slot);
    }
}
