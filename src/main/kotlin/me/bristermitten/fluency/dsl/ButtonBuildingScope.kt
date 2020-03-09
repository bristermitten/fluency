package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.button.ButtonBuilder
import org.bukkit.event.inventory.ClickType

class ButtonBuildingScope(private val builder: ButtonBuilder) {

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

	val lore: LoreList
		get() {
			val loreList = LoreList(builder.lore())
			builder.lore(loreList)
			return loreList
		}

	fun lore(vararg lore: String) {
		builder.lore(*lore)
	}

	fun addLore(vararg lore: String) {
		lore.forEach { builder.addLore(it) }
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

	fun onClick(clickType: ClickType, function: HandlerBuildingScope.() -> Unit) {
		function(HandlerBuildingScope(builder.onClick(clickType)))
	}

	fun onClick(function: HandlerBuildingScope.() -> Unit) {
		function(HandlerBuildingScope(builder.onClick()))
	}
}
