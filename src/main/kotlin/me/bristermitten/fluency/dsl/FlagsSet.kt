package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.button.ButtonBuilder
import org.bukkit.inventory.ItemFlag

class FlagsSet(private val builder: ButtonBuilder) : MutableSet<ItemFlag> by builder.flags() {

    override fun add(element: ItemFlag): Boolean {
        builder.addFlags(element)
        return true
    }
}
