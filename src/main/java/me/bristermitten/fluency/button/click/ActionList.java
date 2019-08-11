package me.bristermitten.fluency.button.click;

import java.util.ArrayList;

public class ActionList extends ArrayList<ClickAction> implements ClickHandler {
    @Override
    public void accept(MenuClickEvent e) {
        forEach(a -> a.accept(e));
    }
}
