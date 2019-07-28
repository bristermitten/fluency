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
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Collectors;

public class Menu {
    protected final List<MenuButton> buttons;
    private final PageList pages;
    private final int maxSize;

    protected Inventory inventory;

    //LegacyMenu Info
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
        return addButton(button, pagedFirstEmpty());
    }

    private int pagedFirstEmpty() {
        int firstEmpty = inventory.firstEmpty();
        if (firstEmpty != -1) return firstEmpty;
        firstEmpty = 0;
        ListIterator<Menu> pageIterator = pages.pageIterator();
        if (pageIterator.hasNext()) {
            do {
                Menu next = pageIterator.next();
                int tempFirstEmpty = next.inventory.firstEmpty();
                if (tempFirstEmpty == -1) {
                    firstEmpty += next.maxSize;
                } else return firstEmpty + tempFirstEmpty;
            } while (firstEmpty == -1 && pageIterator.hasNext());
        }
        Page newPage = addPage();
        System.out.println(pages.stream().mapToLong(p->p.getButtons().size()).sum());
        return firstEmpty + newPage.inventory.firstEmpty();
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
        System.out.println("slot = " + slot);
        page.buttons.set(slot, button);
        page.update();
        return this;
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
}
