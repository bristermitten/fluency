package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.Util;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.data.ButtonHolder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

class ButtonBuilderImpl implements ButtonBuilder {
    private final MenuBuilder parent;
    private ButtonHolder button;
    private HandlerBuilder handlerBuilder;
    private List<Consumer<ItemMeta>> queuedTasks;

    public ButtonBuilderImpl(Fluency fluency, MenuBuilder parent) {
        this.parent = parent;
        this.button = new ButtonHolder(new MenuButton());
        this.queuedTasks = new ArrayList<>();
        this.handlerBuilder = fluency.buildHandler(this);
        button.get().handler(handlerBuilder.build());
        amount(1);
    }

    @Override
    public ButtonBuilder amount(int amount) {
        transform(b -> b.setAmount(amount));
        return this;
    }

    @Override
    public ButtonBuilder type(Material type) {
        transform(b -> b.setType(type));
        return this;
    }

    private void transform(Consumer<MenuButton> c) {
        MenuButton menuButton = button.get();
        c.accept(menuButton);
        button.set(menuButton);
    }

    @Override
    public ButtonBuilder data(short data) {
        transform(b -> b.setDurability(data));
        return this;
    }

    @Override
    public ButtonBuilder name(String name) {
        Objects.requireNonNull(name);
        transformMeta(m -> m.setDisplayName(Util.color(name)));
        return this;
    }

    @Override
    public ButtonBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    @Override
    public ButtonBuilder lore(List<String> lore) {
        lore.stream().map(Util::color).forEach(this::addLore);
        return this;
    }

    @Override
    public ButtonBuilder addLore(String lore) {
        transformMeta(m -> {
            List<String> l = m.getLore();
            if (l == null) l = new ArrayList<>();
            l.add(Util.color(lore));
            m.setLore(l);
        });
        return this;
    }

    @Override
    public ButtonBuilder unbreakable() {
        transformMeta(m -> m.spigot().setUnbreakable(true));
        return this;

    }

    @Override
    public ButtonBuilder enchant(Enchantment e, int level) {
        transform(b -> b.addEnchantment(e, level));
        return this;
    }

    private void transformMeta(Consumer<ItemMeta> f) {
        transform(b -> {
            ItemMeta itemMeta = b.getItemMeta();
            if (itemMeta == null) {
                queuedTasks.add(f);
                return;
            }
            queuedTasks.forEach(q -> q.accept(itemMeta));
            queuedTasks.clear();
            f.accept(itemMeta);
            b.setItemMeta(itemMeta);
        });
    }

    @Override
    public HandlerBuilder onClick() {
//        HandlerBuilder handlerBuilder = fluency.buildHandler(this);
//        transform(b -> {
//            ClickHandler build = handlerBuilder.build();
//            if (b.handler() instanceof ActionList && build instanceof ActionList) {
//                ((ActionList) b.handler()).addAll((ActionList) build);
//            } else
//                b.handler(build);
//        });
        return handlerBuilder;
    }

    @Override
    public HandlerBuilder onClick(ClickType type) {
//        HandlerBuilder handlerBuilder = fluency.buildHandler(this);
        handlerBuilder.whenClickType(type);
//        transform(b -> {
//            ClickHandler build = handlerBuilder.build();
//            if (b.handler() instanceof ActionList && build instanceof ActionList) {
//                ((ActionList) b.handler()).addAll((ActionList) build);
//            } else
//                b.handler(build);
//        });
        return handlerBuilder;
    }

    @Override
    public void invalidate() {
        button = null;
    }

    @Override
    public MenuButton build() {
        return button.get();
    }

    @Override
    public ButtonHolder buildHolder() {
        return button;
    }


    @Override
    public MenuBuilder done() {
        return parent;
    }
}
