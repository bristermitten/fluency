package me.bristermitten.fluency

import me.bristermitten.fluency.button.MenuButton
import me.bristermitten.fluency.dsl.ButtonBuildingScope
import me.bristermitten.fluency.dsl.MenuBuildingScope
import me.bristermitten.fluency.menu.Menu

fun Fluency.createMenu(builder: MenuBuildingScope.() -> Unit): Menu {
    val menu = buildMenu()
    val scope = MenuBuildingScope(menu)
    builder(scope)
    return menu.build()
}


fun Fluency.createButton(builder: ButtonBuildingScope.() -> Unit): MenuButton {
    val button = buildButton()
    val scope = ButtonBuildingScope(button)
    builder(scope)
    return button.build()
}
