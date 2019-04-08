/*
 * MIT License
 *
 * Copyright (c) 2019 Alexander Leslie John Wood
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.bristermitten.spigotmenus.menu.button.builder;

import me.bristermitten.spigotmenus.menu.Menu;
import me.bristermitten.spigotmenus.menu.MenuClickEvent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MenuButtonClickEventBuilder {
    private final MenuButtonBuilder buttonBuilderReturnTo;
    private final Queue<ClickAction> tasks = new LinkedList<>();

    private Consumer<MenuClickEvent> built;
    private boolean modified = false;
    private Queue<Predicate<MenuClickEvent>> tempConditions = new LinkedList<>();

    MenuButtonClickEventBuilder(MenuButtonBuilder previous) {
        this.buttonBuilderReturnTo = previous;
    }


    public MenuButtonClickEventBuilder ifSlotIs(int slot) {
        tempConditions.add(e -> e.getSlot() == slot);
        return this;
    }

    public MenuButtonClickEventBuilder ifTrue(Supplier<Boolean> supplier) {
        tempConditions.add(e -> supplier.get());
        return this;
    }

    public MenuButtonClickEventBuilder ifTrue(Predicate<MenuClickEvent> check) {
        tempConditions.add(check);
        return this;
    }

    public MenuButtonClickEventBuilder otherwise() {
        this.tempConditions = inverseTempConditions();
        return this;
    }


    private Queue<Predicate<MenuClickEvent>> inverseTempConditions() {
        Queue<Predicate<MenuClickEvent>> list = new LinkedList<>();
        for (Predicate<MenuClickEvent> tempCondition : tempConditions) {
            list.add(e -> !tempCondition.test(e));
        }
        return list;
    }

    public MenuButtonClickEventBuilder thenOpenMenu(Menu menu) {
        return thenAction(e -> menu.open(e.getWhoClicked()));
    }

    public MenuButtonClickEventBuilder thenCloseMenu() {
        return thenAction(e -> e.getWhoClicked().closeInventory());
    }

    public MenuButtonClickEventBuilder thenAction(Consumer<MenuClickEvent> onClick) {
        return thenAction(onClick, false);
    }

    public MenuButtonClickEventBuilder clearConditions() {
        tempConditions.clear();
        modified=true;
        return this;
    }

    public MenuButtonClickEventBuilder thenAction(Consumer<MenuClickEvent> onClick, boolean resetConditions) {
        tasks.add(new ClickAction(onClick, tempConditions));
        if (resetConditions)
            tempConditions = new LinkedList<>();
        modified=true;
        return this;
    }

    public MenuButtonBuilder done() {
        return buttonBuilderReturnTo;
    }

    Consumer<MenuClickEvent> build() {
        if (built != null && !modified) {
            return built;
        }
        Consumer<MenuClickEvent> newBuilt = e -> tasks.forEach(t -> t.perform(e));
        modified = false;
        return built = newBuilt;
    }

    public MenuButtonClickEventBuilder thenCancel() {
        return thenAction(e -> e.setCancelled(true));
    }
}
