package me.bristermitten.fluency.dsl

import me.bristermitten.fluency.button.MenuButton
import me.bristermitten.fluency.button.distribution.ButtonDistribution
import me.bristermitten.fluency.menu.MenuBuilder
import org.bukkit.entity.Player

class MenuBuildingScope(val builder: MenuBuilder) {

    var title: String
        get() = builder.title()
        set(value) {
            builder.title(value)
        }

    var size: Int
        get() = builder.size()
        set(value) {
            builder.size(value)
        }

    var distribution: ButtonDistribution
        get() = builder.distribution()
        set(value) {
            builder.distributed(value)
        }

    var background: MenuButton? = null
        set(value) {
            builder.background(value)
            field = value
        }


    inline fun background(function: ButtonBuildingScope.() -> Unit) {
        val button = builder.buildBackground()
        function(ButtonBuildingScope(button))
    }

    fun addButton(button: MenuButton) {
        builder.addButton(button)
    }
//
//    inline fun addButton(slot: Int, function: ButtonBuildingScope.() -> Unit) {
//
//    }

    inline fun addButton(function: ButtonBuildingScope.() -> Unit) {
        val button = builder.buildButton()
        function(ButtonBuildingScope(button))
    }


    inline fun <T> addTemplate(function: TemplateBuildingScope<T>.() -> Unit) {
        val template = builder.buildTemplate<T>()
        function(TemplateBuildingScope(template))
    }

    inline fun addPlayerTemplate(function: TemplateBuildingScope<Player>.() -> Unit) {
        function(TemplateBuildingScope(builder.buildPlayerTemplate()))
    }

    fun skip() = builder.skip()
}
