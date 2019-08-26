package me.bristermitten.fluency.button;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Before;
import org.junit.Test;

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

        MenuButton hello = builder.buildButton().name("Hello").build();
        System.out.println(hello);
    }
}
