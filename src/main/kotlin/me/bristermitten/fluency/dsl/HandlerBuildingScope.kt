package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.button.click.ClickHandler
import me.bristermitten.fluency.button.click.HandlerBuilder
import me.bristermitten.fluency.button.click.MenuClickEvent
import me.bristermitten.fluency.menu.Menu
import org.bukkit.entity.Player

class HandlerBuildingScope(private val builder: HandlerBuilder) {

	fun cancel() {
		builder.cancel()
	}

	fun close() {
		builder.closeMenu()
	}

	fun open(menu: Menu) {
		builder.openMenu(menu)
	}

	fun open(menu: () -> Menu) {
		builder.openMenu(menu)
	}

	fun sendMessage(message: String) {
		sendMessage { message }
	}

	fun sendMessage(message: (MenuClickEvent) -> String) {
		builder.sendMessage(message)
	}

	fun action(action: Player.() -> Unit) {
		action(ClickHandler {
			action(it.clicker())
		})
	}

	fun action(action: ClickHandler) {
		builder.action(action)
	}

	fun `when`(condition: (MenuClickEvent) -> Boolean, scope: HandlerBuildingScope.() -> Unit) {
		scope(HandlerBuildingScope(builder.`when`(condition)))
	}

	fun whenPlayer(condition: Player.() -> Boolean, scope: HandlerBuildingScope.() -> Unit) {
		return `when`({
			condition(it.clicker())
		}, scope)
	}

	fun otherwise(scope: HandlerBuildingScope.() -> Unit) {
		scope(HandlerBuildingScope(builder.otherwise()))
	}
}
