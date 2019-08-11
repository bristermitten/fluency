package me.bristermitten.fluency.data;

import me.bristermitten.fluency.menu.Menu;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * A custom implementation of {@link LinkedList} used for
 * managing a list of pages and a main menu.
 * However, to avoid {@link StackOverflowError} exceptions we don't use
 * the main menu in {@link PageList#equals(Object)} as the only use case would be
 * in {@link Menu#equals(Object)}
 */
public class PageList extends LinkedList<Menu> {

    public PageList(Menu mainPage) {
        add(mainPage);
    }

    public ListIterator<Menu> pageIterator() {
        return listIterator(1);
    }

    @NotNull
    @Override
    public Iterator<Menu> iterator() {
        return listIterator();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        PageList other = (PageList) o;
        if (other.size() != size()) return false;
        for (int i = 1; i < other.size(); i++) {
            if (!other.get(i).equals(get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        Iterator<Menu> it = pageIterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            Menu e = it.next();
            sb.append(e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    public int hashCode() {
        int hashCode = 1;
        Iterator i = pageIterator();
        while (i.hasNext()) {
            Object obj = i.next();
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
        }
        return hashCode;
    }

    public void forEachPage(Consumer<? super Menu> action) {
        subList(1, size()).forEach(action);
    }
}
