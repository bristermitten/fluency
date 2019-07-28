package me.bristermitten.spigotmenus;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.bristermitten.spigotmenus.menu.Menu;
import me.bristermitten.spigotmenus.menu.button.builder.MenuBuilder;
import me.bristermitten.spigotmenus.menu.button.builder.MenuButtonBuilder;
import org.bukkit.Material;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MenuBuilderTests {
    private static ServerMock server;

    @BeforeClass
    public static void init() {
        server = MockBukkit.mock();
        MockBukkit.load(SpigotMenusTestPlugin.class);
    }

    @AfterClass
    public void unload() {
        MockBukkit.unload();
        server = null;
    }

    @Test
    public void testSimpleCreate() {
        Menu test = new MenuBuilder(9, "Test").build();
        Menu test2 = new Menu(9, "Test");
        assertEquals(test, test2);
    }

    @Test
    public void testAddingButtons() {
        Menu test = new MenuBuilder(9, "Test").buildButton().setType(Material.STONE)
                .buildAndAddToMenu().build();
        Menu test2 = new Menu(9, "Test");
        test2.addButton(new MenuButtonBuilder().setType(Material.STONE).buildButton());

        System.out.println(test.getButton(0).equals(test2.getButton(0)));
        assertEquals(test, test2);
    }

}
