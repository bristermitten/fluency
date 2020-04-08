package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.button.ButtonBuilder
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemFlag
import org.jetbrains.annotations.NotNull

@Suppress("unused")
class ButtonBuildingScope(val builder: ButtonBuilder) {

    var amount
        get() = builder.amount()
        set(value) {
            builder.amount(value)
        }

    var type
        get() = builder.type()
        set(value) {
            builder.type(value)
        }

    var data
        get() = builder.data()
        set(value) {
            builder.data(value)
        }

    var name
        get() = builder.name() ?: ""
        set(value) {
            builder.name(value)
        }

    var enchants: MutableMap<Enchantment, Int>
        get() = EnchantmentMap(builder)
        set(value) {
            for ((k, v) in value) {
                builder.enchant(k, v)
            }
        }

    var flags: MutableSet<ItemFlag>
        get() = builder.flags()
        set(value) {
            for (flag in value) {
                builder.addFlags(flag)
            }
        }

    val lore: LoreList = LoreList(builder)

    fun lore(vararg lore: String) {
        builder.lore(*lore)
    }

    fun addLore(vararg lore: String) {
        lore.forEach { builder.addLore(it) }
    }

    fun loreString(lore: String) {
        builder.lore(lore.split("\n"))
    }

    var unbreakable
        get() = builder.isUnbreakable
        set(value) {
            if (value) {
                builder.unbreakable()
            } else builder.breakable()
        }

    fun unbreakable() = builder.unbreakable()
    fun breakable() = builder.breakable()

    inline fun onClick(clickType: ClickType, function: HandlerBuildingScope.() -> Unit) {
        val click = builder.onClick(clickType)
        function(HandlerBuildingScope(click))
    }

    inline fun onClick(function: HandlerBuildingScope.() -> Unit) {
        val click = builder.onClick()
        function(HandlerBuildingScope(click))
    }
}
