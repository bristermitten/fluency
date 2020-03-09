package me.bristermitten.fluency

import me.bristermitten.fluency.dsl.MenuBuildingScope
import me.bristermitten.fluency.menu.Menu

fun Fluency.createMenu(builder: MenuBuildingScope.() -> Unit) : Menu {
	val menu = buildMenu()
	val scope = MenuBuildingScope(menu)
	builder(scope)
	return menu.build()
}
