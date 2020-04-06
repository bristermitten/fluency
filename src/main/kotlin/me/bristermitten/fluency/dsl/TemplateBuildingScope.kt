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

	init {
		builder.loreFrom { lore }
	}

	private val lore: MutableList<String> = ArrayList()

	fun lore(vararg lore: String) {
		this.lore.clear()
		this.lore.addAll(lore)
	}

	fun addLore(vararg lore: String) {
		lore.forEach { this.lore.add(it) }
	}

	var source: T? = null
		set(value) {
			builder.withSource(value)
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
		builder.withSource(it)
	}

	fun source(function: () -> T) {
		builder.withSource(function)
	}

	inline fun onClick(function: HandlerBuildingScope.() -> Unit) {
		val click = builder.onClick()
		function(HandlerBuildingScope(click))
	}
}

