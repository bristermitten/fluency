package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.Util

/**
 * Delegates to a list but adds color to added strings
 * Provides compatibility for Kotlin `+=`
 */
class LoreList(private val list: MutableList<String>) : List<String> by list {

	operator fun plusAssign(element: String) {
		list.add(Util.color(element))
	}
}
