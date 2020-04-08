package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.button.ButtonBuilder
import org.bukkit.enchantments.Enchantment

class EnchantmentMap(private val buttonBuilder: ButtonBuilder) : MutableMap<Enchantment, Int> by buttonBuilder.enchantments() {

    override fun put(key: Enchantment, value: Int): Int? {
        buttonBuilder.enchant(key, value)
        return null
    }
}
