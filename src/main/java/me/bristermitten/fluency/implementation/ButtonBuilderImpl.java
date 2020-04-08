package me.bristermitten.fluency.implementation;

import com.google.common.collect.ImmutableMap;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

class ButtonBuilderImpl implements ButtonBuilder {
    private final MenuBuilder parent;
    private final ButtonHolder button;
    private HandlerBuilder<ButtonBuilder> handlerBuilder;
    private List<Consumer<ItemMeta>> modifierQueue;

    public ButtonBuilderImpl(Fluency fluency, MenuBuilder parent) {
        this.parent = parent;
        this.modifierQueue = new ArrayList<>();
        this.handlerBuilder = fluency.buildHandler(this);

        MenuButton button = new MenuButton();
        button.handler(handlerBuilder.build());
        this.button = new ButtonHolder(button);
        amount(1);
        lore(new ArrayList<>());
    }

    @NotNull
    @Override
    public ButtonBuilder amount(int amount) {
        transformItem(b -> b.setAmount(amount));
        return this;
    }

    @Override
    public int amount() {
        return button.get().getAmount();
    }

    @NotNull
    @Override
    public ButtonBuilder type(Material type) {
        transformItem(b -> b.setType(type));
        return this;
    }

    @NotNull
    @Override
    public Material type() {
        return button.get().getType();
    }

    @NotNull
    @Override
    public ButtonBuilder data(short data) {
        transformItem(b -> b.setDurability(data));
        return this;
    }

    @Override
    public short data() {
        return button.get().getDurability();
    }

    @NotNull
    @Override
    public ButtonBuilder name(@Nullable String name) {
        transformMeta(m -> m.setDisplayName(Util.color(name)));
        return this;
    }

    @Nullable
    @Override
    public String name() {
        ItemMeta itemMeta = button.get().getItemMeta();
        if (itemMeta == null) return null;
        return itemMeta.getDisplayName();
    }

    @NotNull
    @Override
    public ButtonBuilder lore(@NotNull String... lore) {
        return lore(Arrays.asList(lore));
    }

    @NotNull
    @Override
    public ButtonBuilder lore(@NotNull List<String> lore) {
        transformMeta(meta -> {
            List<String> loreList = new ArrayList<>();
            for (String s : lore) {
                loreList.add(Util.color(s));
            }
            meta.setLore(loreList);
        });
        return this;
    }

    @NotNull
    @Override
    public ButtonBuilder addLore(String... lore) {
        transformMeta(meta -> {
            List<String> loreList = new ArrayList<>();
            if (meta.hasLore()) loreList = meta.getLore();
            for (String s : lore) {
                loreList.add(Util.color(s));
            }
            meta.setLore(loreList);
        });
        return this;
    }

    @Override
    @NotNull
    public List<String> lore() {
        ItemMeta itemMeta = button.get().getItemMeta();
        if (itemMeta == null || !itemMeta.hasLore()) return new ArrayList<>();
        return itemMeta.getLore();
    }

    @NotNull
    @Override
    public ButtonBuilder unbreakable() {
        transformMeta(m -> m.spigot().setUnbreakable(true));
        return this;
    }

    @Override
    public @NotNull ButtonBuilder breakable() {
        transformMeta(m -> m.spigot().setUnbreakable(false));
        return this;
    }

    @Override
    public boolean isUnbreakable() {
        ItemMeta itemMeta = button.get().getItemMeta();
        if (itemMeta == null) return false;
        return itemMeta.spigot().isUnbreakable();
    }

    @NotNull
    @Override
    public ButtonBuilder enchant(Enchantment enchantment, int level) {
        transformItem(b -> b.addUnsafeEnchantment(enchantment, level));
        return this;
    }

    @Override
    public @NotNull Map<Enchantment, Integer> enchantments() {
        return button.get().getEnchantments();
    }

    @Override
    public @NotNull ButtonBuilder addFlags(ItemFlag... flags) {
        transformMeta(m -> m.addItemFlags(flags));
        return this;
    }

    @Override
    public @NotNull Set<ItemFlag> flags() {
        ItemMeta meta = button.get().getItemMeta();
        if (meta == null) {
            return Collections.emptySet();
        }
        return meta.getItemFlags();
    }

    @NotNull
    @Override
    public HandlerBuilder<ButtonBuilder> onClick() {
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

    @NotNull
    @Override
    public HandlerBuilder<ButtonBuilder> onClick(ClickType type) {
        handlerBuilder.whenClickType(type);
        return handlerBuilder;
    }


    @NotNull
    @Override
    public MenuButton build() {
        return button.get();
    }

    @NotNull
    @Override
    public ButtonHolder getHolder() {
        return button;
    }

    @Override
    public MenuBuilder done() {
        return parent;
    }

    private void transformItem(Consumer<MenuButton> transformer) {
        MenuButton menuButton = button.get();
        transformer.accept(menuButton);
        button.set(menuButton);
    }

    private void transformMeta(Consumer<ItemMeta> f) {
        transformItem(b -> {
            ItemMeta itemMeta = b.getItemMeta();

            if (itemMeta == null) {
                modifierQueue.add(f);
                return;
            }

            modifierQueue.forEach(q -> q.accept(itemMeta));
            modifierQueue.clear();
            f.accept(itemMeta);
            b.setItemMeta(itemMeta);
        });
    }
}
