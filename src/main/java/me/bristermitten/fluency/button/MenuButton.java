package me.bristermitten.fluency.button;

import me.bristermitten.fluency.button.click.ClickHandler;
import me.bristermitten.fluency.button.click.Handlers;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MenuButton extends ItemStack {
    private ClickHandler handler;

    public MenuButton() {
        super();
        this.handler = Handlers.DO_NOTHING;
    }

    public MenuButton(Material type, ClickHandler handler) {
        super(type);
        this.handler = handler;
    }


    public void handler(ClickHandler handler) {
        this.handler = handler;
    }

    public ClickHandler handler() {
        return handler;
    }


}
