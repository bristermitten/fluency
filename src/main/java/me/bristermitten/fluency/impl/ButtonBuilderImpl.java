package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.Util;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

class ButtonBuilderImpl implements ButtonBuilder {
    private final Fluency fluency;
    private final MenuBuilder parent;
    private MenuButton button;

    public ButtonBuilderImpl(Fluency fluency, MenuBuilder parent) {
        this.fluency = fluency;
        this.parent = parent;
        this.button = new MenuButton();
    }

    @Override
    public ButtonBuilder amount(int amount) {
        button.setAmount(amount);
        return this;
    }

    @Override
    public ButtonBuilder type(Material type) {
        button.setType(type);
        return this;
    }

    @Override
    public ButtonBuilder data(short data) {
        button.setDurability(data);
        return this;
    }

    @Override
    public ButtonBuilder name(String name) {
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
        button.addEnchantment(e, level);
        return this;
    }

    private void transformMeta(Consumer<ItemMeta> f) {
        ItemMeta itemMeta = button.getItemMeta();
        f.accept(itemMeta);
        button.setItemMeta(itemMeta);
    }

    @Override
    public HandlerBuilder onClick() {
        HandlerBuilder handlerBuilder = fluency.buildHandler(this);
        button.handler(handlerBuilder.build());
        return handlerBuilder;
    }

    @Override
    public void invalidate() {
        button = null;
    }

    @Override
    public MenuButton build() {
        return button;
    }

    @Override
    public MenuBuilder done() {
        return parent;
    }
}
