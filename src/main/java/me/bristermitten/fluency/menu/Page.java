package me.bristermitten.fluency.menu;

import me.bristermitten.fluency.data.PageList;

public class Page extends Menu {
    private final Menu parent;

    public Page(Menu parent) {
        this.parent = parent;
        this.size(parent.size());
        this.title(parent.title());
        this.distribution(parent.distribution().copy());
    }

    @Override
    public PageList pages() {
        return parent.pages();
    }
}
