package me.bristermitten.spigotmenus.menu.button;

import me.bristermitten.spigotmenus.util.Chat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {
    private String name;
    private Material type = Material.AIR;
    private int amount = 1;
    private short data = 0;
    private List<String> lore = new ArrayList<>();

    public ItemBuilder() {
    }

    public ItemBuilder(Material type) {
        this.type = type;
    }

    public ItemBuilder(ItemStack copy) {
        this.amount = copy.getAmount();
        this.type = copy.getType();
        this.data = copy.getDurability();
        if (copy.hasItemMeta()) {
            ItemMeta itemMeta = copy.getItemMeta();
            if (itemMeta.hasLore())
                this.lore = itemMeta.getLore();
            if (itemMeta.hasDisplayName())
                this.name = itemMeta.getDisplayName();
        }
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setData(short data) {
        this.data = data;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        this.lore.addAll(Arrays.asList(lore));
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(type, amount, data);
        ItemMeta itemMeta = item.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(Chat.color(name));
        }
        if (lore != null) {
            itemMeta.setLore(lore.stream().map(Chat::color).collect(Collectors.toList()));
        }
        item.setItemMeta(itemMeta);
        return item;

    }
}
