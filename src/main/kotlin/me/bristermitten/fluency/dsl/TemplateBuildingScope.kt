package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.button.template.TemplateBuilder
import org.bukkit.Material

class TemplateBuildingScope<T>(val builder: TemplateBuilder<T>) {

	var type: Material? = null
		set(value) {
			builder.type(value)
			field = value
		}

	var name: String? = null
		set(value) {
			builder.nameFrom { value }
			field = value
		}


	val lore: LoreList = LoreList(mutableListOf())

	fun lore(vararg lore: String) {
		this.lore.clear()
		this.lore.addAll(lore)
	}

	fun addLore(vararg lore: String) {
		lore.forEach { this.lore.add(it) }
	}

	var source: T? = null
		set(value) {
			builder.withObject(value)
			field = value
		}

	fun type(function: (T) -> Material) {
		builder.typeFrom(function)
	}

	fun name(function: (T) -> String) {
		builder.nameFrom(function)
	}

	fun lore(function: (T) -> List<String>) {
		builder.loreFrom(function)
	}

	fun loreString(function: (T) -> String) {
		builder.loreFrom { function(it).split("\n") }
	}

	fun source(it: T) {
		builder.withObject(it)
	}

	fun source(function: () -> T) {
		builder.withObject(function)
	}


}

