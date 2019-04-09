package me.bristermitten.spigotmenus.menu;

import lombok.Getter;
import lombok.val;
import me.bristermitten.spigotmenus.menu.button.MenuButton;
import me.bristermitten.spigotmenus.menu.button.MenuButtons;
import me.bristermitten.spigotmenus.menu.page.Page;
import me.bristermitten.spigotmenus.util.Chat;
import me.bristermitten.spigotmenus.util.dataclass.FixedCapacityLinkedList;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

import static me.bristermitten.spigotmenus.util.Constants.MAX_INV_SIZE;
import static me.bristermitten.spigotmenus.util.Constants.MAX_PAGE_SIZE;

public class Menu {
    @Getter
    private final LinkedList<MenuButton> buttons;
    private final LinkedList<Menu> pages;
    /**
     * Local maximum size for pagination. Means a menu can have max n rows and still have pages added
     */
    private final int maxSize;
    Inventory inventory;
    //MENU INFO
    private String title;
    private int size;

    public Menu(String title, int size, MenuButton... buttons) {
        Objects.requireNonNull(title, "title");
        Validate.isTrue(size > 0, "size is negative");

        this.title = title;
        this.size = size;
        this.maxSize = size;
        this.buttons = new FixedCapacityLinkedList<>(size, Arrays.asList(buttons));
        this.pages = new LinkedList<>();
        this.pages.add(this);
        updateInfo();
    }

    private void updateInfo() {
        if (size > maxSize) {
            int pagesAmount = pagesNeeded(size);
            for (int i = 0; i < pagesAmount; i++) {
                addPage();
            }
            size = maxSize;
        }
        this.inventory = Bukkit.createInventory(new MenuHolder(this), size, Chat.color(title));

        for (int i = 0; i < buttons.size(); i++) {
            MenuButton menuButton = buttons.get(i);
            ItemStack item = menuButton == null ? null : menuButton.getItem();
            this.inventory.setItem(i, item);
        }
    }

    public Page addPage() {
        int pageNumber = pages.size();
        Page page = createNewPage(pageNumber);

        //copy over previous button if it exists
        Menu lastPage = pages.getLast();
        val lastButton = lastPage.buttons.getLast();
        if (lastButton != null) {
            page.addButton(lastButton, lastPage.size - 1);
        }

        //add next page button
        lastPage.addButton(MenuButtons.NEXT_PAGE, lastPage.size - 1);
        pages.add(page);
        return page;
    }

    private Page createNewPage(int pageNumber) {
        Page page = new Page(title + "Page " + pageNumber, size, this);
        page.addButton(MenuButtons.PREVIOUS_PAGE);
        return page;
    }

    private int firstEmpty() {
        int firstEmpty = inventory.firstEmpty();
        Iterator<Menu> pageIterator = pages.iterator();
        pageIterator.next(); //we've already counted the first page
        while (firstEmpty == -1 && pageIterator.hasNext()) {
            Menu page = pageIterator.next();
            int pageFirstEmpty = page.firstEmpty();
            if (pageFirstEmpty == -1) {
                continue;
            }
            return firstEmpty + pageFirstEmpty;
        }
        if (firstEmpty == -1) {
            addPage();
            return firstEmpty();
        }
        return firstEmpty;
    }

    public void addButton(MenuButton button) {
        addButton(button, firstEmpty());
    }


    public void addButton(MenuButton button, int slot) {
        buttons.set(slot, button);
        updateInfo();
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

    public void open(Player whoClicked) {
        whoClicked.openInventory(inventory);
    }
}
