package me.bristermitten.fluency.button.template;

import me.bristermitten.reflector.examples.simple.SimpleDataClass;
import org.bukkit.Material;

public class TestButtonTemplate extends ButtonTemplate<SimpleDataClass> {
    public TestButtonTemplate() {
        super(null, SimpleDataClass::getName, s -> Material.STONE, s -> null);
    }
}
