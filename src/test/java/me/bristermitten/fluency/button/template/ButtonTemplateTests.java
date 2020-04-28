package me.bristermitten.fluency.button.template;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.SimpleDataClass;
import me.bristermitten.fluency.menu.Menu;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ButtonTemplateTests {
    private Fluency fluency;

    @Before
    public void setUp() {
        BukkitMock.init();
        fluency = Fluency.create(null);
    }
    @Test
    public void testSimpleFunctionality() {

        List<SimpleDataClass> dataClassList = new ArrayList<>();
        dataClassList.add(new SimpleDataClass(3, "1", null));
        dataClassList.add(new SimpleDataClass(4, "2", null));
        dataClassList.add(new SimpleDataClass(5, "3", null));

        MenuBuilder menuBuilder = fluency.buildMenu()
                .buildTemplatesForEach(dataClassList)
                .nameFrom(SimpleDataClass::getName)
                .done();

        Menu build = menuBuilder.build();

        for (int i = 0; i < 3; i++) {
            assertEquals(
                    fluency.buildButton()
                            .name(String.valueOf(i + 1))
                            .build(),
                    build.button(i));
        }
    }

    @Test
    public void testPlayerTemplate() {

        MenuBuilder menuBuilder = fluency.buildMenu()
                .buildPlayerTemplate()
                .type(Material.STONE)
                .nameFrom(Player::getName)
                .done();

        Menu menu = menuBuilder.build();

        PlayerMock player = MockBukkit.getMock().addPlayer();
        menu.open(player);

        ItemStack item = player.getOpenInventory().getTopInventory().getItem(0);
        assertEquals(player.getName(), item.getItemMeta().getDisplayName());
    }
}
