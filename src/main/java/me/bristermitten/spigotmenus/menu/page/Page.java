package me.bristermitten.spigotmenus.menu.page;

import me.bristermitten.spigotmenus.menu.Menu;

public class Page extends Menu {
    private final Menu firstPage;

    public Page(String title, int size, Menu firstPage) {
        super(title, size);
        this.firstPage = firstPage;
    }

    public Page addPage() {
        return firstPage.addPage();
    }
}
