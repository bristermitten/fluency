package me.bristermitten.spigotmenus;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.bristermitten.spigotmenus.menu.Menu;
import me.bristermitten.spigotmenus.menu.MenuClickEvent;
import me.bristermitten.spigotmenus.menu.button.MenuButton;
import me.bristermitten.spigotmenus.menu.button.builder.MenuButtonBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.JVM)
public class MenuTest {

    private static ServerMock server;
    private static SpigotMenusTestPlugin plugin;
    private static Menu menu;
    private static StringWriter log = new StringWriter();

    @BeforeClass
    public static void init() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(SpigotMenusTestPlugin.class);
    }


    @Test
    public void testMenuCreated_withoutException() {
        menu = new Menu("Test Menu", 9);
    }

    @Test
    public void testMenuAddButton() {
        MenuButton testButton = new MenuButtonBuilder().setType(Material.DIRT).onAnyClick()
                .thenAction(e -> log.write("clicked")).done().buildButton();
        menu.addButton(testButton);
        assertEquals(menu.getButton(0), testButton);
    }

    @Test
    public void testMenuButtonClick_logsInfo() {
        MenuClickEvent mock = Mockito.mock(MenuClickEvent.class);
        when(mock.getSlot()).thenReturn(0);
        when(mock.getClick()).thenReturn(ClickType.LEFT);
        menu.getButton(0).getOnClick().accept(mock);
        assertEquals("clicked", log.toString());
    }
}
