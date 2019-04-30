package me.bristermitten.spigotmenus.menu.button;

import me.bristermitten.spigotmenus.util.Chat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple builder class for a {@link ItemStack}
 * This is different to typical builder classes as it builds the data on the fly,
 * with an existing {@link ItemStack}.
 * This means that calling a method on {@link ItemBuilder} also affects the
 * underlying {@link ItemStack} UNLESS {@link ItemBuilder#ItemBuilder(ItemStack, boolean)}
 * is called with {@code true}
 */
public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    public ItemBuilder(Material type) {
        this.itemStack = new ItemStack(type);
    }

    public ItemBuilder(ItemStack copy) {
        this(copy, false);
    }

    public ItemBuilder(ItemStack copy, boolean clone) {
        this.itemStack = clone ? copy.clone() : copy;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setData(short data) {
        this.itemStack.setDurability(data);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(lore.stream().map(Chat::color).collect(Collectors.toList()));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Chat.color(name));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setType(Material type) {
        this.itemStack.setType(type);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        List<String> currentLore = this.itemStack.getItemMeta().getLore();
        List<String> newLore = Arrays.stream(lore).map(Chat::color).collect(Collectors.toList());
        if (currentLore == null) currentLore = newLore;
        else currentLore.addAll(newLore);
        setLore(currentLore);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}
