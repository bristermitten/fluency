package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.button.ButtonBuilder

/**
 * Delegates to a list but adds color to added strings
 * Provides compatibility for Kotlin `+=`
 */
class LoreList(private val buttonBuilder: ButtonBuilder) : MutableList<String> by buttonBuilder.lore()
{

	override val size: Int
		get() = buttonBuilder.lore().size

	operator fun plusAssign(element: String)
	{
		buttonBuilder.addLore(element)
	}

	override fun add(element: String): Boolean
	{
		buttonBuilder.addLore(element)
		return true
	}

	override fun clear()
	{
		buttonBuilder.lore(ArrayList())
	}
}
