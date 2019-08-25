package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static me.bristermitten.fluency.button.distribution.ButtonDistribution.SIMPLE;
import static org.junit.Assert.assertEquals;

public class ButtonBuilderTest {

    private Fluency fluency;
    private ButtonBuilder builder;

    @Before
    public void setUp() {
        BukkitMock.init();
        fluency = Fluency.create(null);
        builder = fluency.buildButton();
    }

    @Test
    public void testName() {
        MenuButton expected = new MenuButton();
        expected.setAmount(1);
        ItemMeta itemMeta = expected.getItemMeta();
        itemMeta.setDisplayName("Hello");
        expected.setItemMeta(itemMeta);

        MenuButton actual = builder.name("Hello").build();
        assertEquals(expected, actual);
    }

    @Test
    public void testChaining() {
        MenuBuilder builder = fluency.buildMenu().size(3 * 9).title("Test Menu").distributed(SIMPLE);

        builder.buildButton()
                .name("Test Button")
                .onClick().cancel().when(ThreadLocalRandom.current().nextBoolean())
                .action(e-> System.out.println(true))
                .otherwise()
                .action(e-> System.out.println(false))
                .done().build();
    }
}
