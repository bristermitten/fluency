package me.bristermitten.fluency.button.click;

import me.bristermitten.fluency.data.PageList;
import me.bristermitten.fluency.menu.Menu;

public class Handlers {
    public static final ClickHandler DO_NOTHING = e -> {
    };
    public static final ClickHandler CANCEL = e -> e.setCancelled(true),
            NEXT_PAGE = e -> changePage(e, 1),
            PREVIOUS_PAGE = e -> changePage(e, -1);

    private static void changePage(MenuClickEvent e, int change) {
        PageList pages = e.menu().pages();
        int i = pages.indexOf(e.menu());
        System.out.println(i);
        int newIndex = i + change;
        if (newIndex < 0 || newIndex >= pages.size()) return;
        Menu menu = pages.get(newIndex);
        menu.open(e.getWhoClicked());
    }
}
