package me.bristermitten.spigotmenus.menu;

import me.bristermitten.spigotmenus.menu.button.MenuButton;
import me.bristermitten.spigotmenus.menu.button.MenuButtons;
import me.bristermitten.spigotmenus.menu.page.Page;
import me.bristermitten.spigotmenus.util.Chat;
import me.bristermitten.spigotmenus.util.dataclass.FixedCapacityArrayList;
import me.bristermitten.spigotmenus.util.dataclass.PageList;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Menu {
    protected final List<MenuButton> buttons;
    private final PageList pages;
    private final int maxSize;

    protected Inventory inventory;

    //Menu info
    private String title;
    private int size;

    public Menu(int size, String title) {
        Objects.requireNonNull(title, "title");
        Validate.isTrue(size > 0, "size is negative");

        this.title = title;
        this.size = size;
        this.maxSize = size;
        this.buttons = new FixedCapacityArrayList<>(size);
        this.pages = new PageList(this);
        update();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public List<MenuButton> getButtons() {
        return buttons;
    }

    public Menu addButton(MenuButton button) {
        MenuSlot slot = pagedFirstEmpty();
        return pages.get(slot.pageIndex).addButtonLocally(button, slot.pageSlot);
    }

    private MenuSlot pagedFirstEmpty() {
        Optional<Menu> first = pages.stream().filter(m -> m.inventory.firstEmpty() != -1).findFirst();
        if (first.isPresent()) {
            Menu menu = first.get();
            return new MenuSlot(pages.indexOf(menu), menu.inventory.firstEmpty());
        }
        Page newPage = addPage();
        return new MenuSlot(pages.indexOf(newPage),newPage.inventory.firstEmpty());
    }

    private Page addPage() {
        Menu last = pages.getLast();
        Page page = new Page(maxSize, title, this);
        page.addButtonLocally(MenuButtons.PREVIOUS_PAGE, 0);

        MenuButton lastButton = last.buttons.get(last.buttons.size() - 1);
        if (lastButton != null)
            page.addButton(lastButton);
        last.addButtonLocally(MenuButtons.NEXT_PAGE, last.maxSize - 1);
        pages.add(page);
        return page;
    }

    public Menu addButton(MenuButton button, int slot) {
        Menu page = pageFor(slot);
        return page.addButtonLocally(button, slot % page.maxSize);

    }

    public Menu addButtonLocally(MenuButton button, int slot) {
        buttons.set(slot, button);
        update();
        return this;
    }

    private void update() {
        update(true);
    }

    private void update(boolean updateSub) {
        this.inventory = Bukkit.createInventory(new MenuHolder(this), size, Chat.color(title));
        for (int i = 0; i < buttons.size(); i++) {
            MenuButton menuButton = buttons.get(i);
            ItemStack item = menuButton == null ? null : menuButton.getItem();
            this.inventory.setItem(i, item);
        }
        if (updateSub)
            this.pages.forEach(page -> update(false));
    }

    private Menu pageFor(int slot) {
        if (slot < size) return this;
        if (slot >= pages.lastSlot())
            throw new IndexOutOfBoundsException("LegacyMenu cannot fit slot " + slot + ", max slot: " + pages.lastSlot());
        int index = slot / maxSize - 1;
        return pages.get(index);
    }

    public PageList getPages() {
        return pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        pages.forEach(p -> p.setSize(size));
        update();
    }

    public void open(Player whoClicked) {
        whoClicked.openInventory(inventory);
    }

    public void setTitle(String title) {
        this.title = title;
        pages.forEach(p -> p.setTitle(title));
        update();
    }

    public MenuButton getButton(int i) {
        Menu menu = pageFor(i);
        int slot = i - pages.indexOf(menu) * maxSize;
        return menu.buttons.get(slot);
    }

    public List<MenuButton> getAllButtons() {
        return pages.stream().flatMap(m -> m.getButtons().stream()).collect(Collectors.toList());
    }

    private static class MenuSlot {
        private final int pageIndex;
        private final int pageSlot;

        private MenuSlot(int pageIndex, int pageSlot) {
            this.pageIndex = pageIndex;
            this.pageSlot = pageSlot;
        }
    }
}
