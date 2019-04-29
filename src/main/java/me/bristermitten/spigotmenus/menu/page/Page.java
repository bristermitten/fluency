package me.bristermitten.spigotmenus.menu.page;

import me.bristermitten.spigotmenus.menu.Menu;

import java.util.LinkedList;

public class Page extends Menu {
    private final Menu firstPage;

    public Page(String title, int size, Menu firstPage) {
        super(title, size);
        this.firstPage = firstPage;
    }

    @Override
    public Page addPage() {
        return firstPage.addPage();
    }

    @Override
    public LinkedList<Menu> getPages() {
        return firstPage.getPages();
    }
}
