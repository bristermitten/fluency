package me.bristermitten.fluency.button.click;

import me.bristermitten.fluency.data.PageList;

public class Handlers {
    public static final ClickHandler DO_NOTHING = e -> {
    };
    public static final ClickHandler CANCEL = e -> e.setCancelled(true),
            NEXT_PAGE = e -> changePage(e, 1),
            PREVIOUS_PAGE = e -> changePage(e, -1);

    private static void changePage(MenuClickEvent e, int change) {
        PageList pages = e.menu().pages();
        int i = pages.indexOf(e.menu());
        int newIndex = i + change;
        if (newIndex < 0 || newIndex >= pages.size()) return;
        pages.get(newIndex).open(e.getWhoClicked());
    }
}
