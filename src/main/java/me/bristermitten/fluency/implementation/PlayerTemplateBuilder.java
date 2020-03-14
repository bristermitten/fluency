package me.bristermitten.fluency.implementation;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class PlayerTemplateBuilder extends TemplateBuilderImpl<Player> {

	public PlayerTemplateBuilder(Fluency fluency, MenuBuilder parent) {
		super(fluency, parent);
		template.requiresPlayer(true);
		template.sourceFunction(Function.identity());
	}
}
