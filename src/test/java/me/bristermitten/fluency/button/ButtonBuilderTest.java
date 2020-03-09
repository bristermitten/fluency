package me.bristermitten.fluency.button;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.menu.MenuBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Before;
import org.junit.Test;

import static me.bristermitten.fluency.button.distribution.ButtonDistribution.simple;
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
        MenuBuilder builder = fluency.buildMenu().size(3 * 9).title("Test Menu").distributed(simple);


        MenuButton hello = builder.buildButton().name("Hello").build();
        //TODO implement
//        System.out.println(hello);
    }
}
