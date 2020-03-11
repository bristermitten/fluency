package me.bristermitten.fluency

import me.bristermitten.fluency.button.MenuButton
import me.bristermitten.fluency.menu.Menu


operator fun Menu.get(index: Int) : MenuButton? {
	return button(index)
}
