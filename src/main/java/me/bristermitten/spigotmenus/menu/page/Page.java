package me.bristermitten.spigotmenus.menu.page;

import me.bristermitten.spigotmenus.menu.Menu;
import me.bristermitten.spigotmenus.util.dataclass.PageList;

public class Page extends Menu {
    private final Menu mainMenu;

    public Page(int size, String title, Menu mainMenu) {
        super(size, title);
        this.mainMenu = mainMenu;
    }

    @Override
    public PageList getPages() {
        return mainMenu.getPages();
    }
}
