package me.bristermitten.spigotmenus;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.bristermitten.spigotmenus.menu.Menu;
import me.bristermitten.spigotmenus.menu.MenuClickEvent;
import me.bristermitten.spigotmenus.menu.MenuHolder;
import me.bristermitten.spigotmenus.menu.button.MenuButton;
import me.bristermitten.spigotmenus.menu.button.MenuButtons;
import me.bristermitten.spigotmenus.menu.button.builder.MenuButtonBuilder;
import org.bukkit.Material;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.bukkit.event.inventory.ClickType.LEFT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.JVM)
public class MenuTest {

    private static ServerMock server;
    private static Menu menu;
    private static StringWriter log = new StringWriter();
    private static MenuButton testButton;

    @BeforeClass
    public static void init() {
        server = MockBukkit.mock();
        MockBukkit.load(SpigotMenusTestPlugin.class);
        testButton = new MenuButtonBuilder().setType(Material.DIRT).onAnyClick()
                .thenAction(e -> log.write("clicked")).done().buildButton();
    }

    @Before
    public void initMenu() {
        menu = new Menu(9, "Test LegacyMenu");
    }

    @After
    public void reset() {
        log = new StringWriter();
    }

    @Test
    public void testMenuAddButton() {
        menu.addButton(testButton);
        assertEquals(testButton, menu.getButton(0));
    }

    @Test
    public void testMenuButtonClick_logsInfo() {
        MenuClickEvent mock = Mockito.mock(MenuClickEvent.class);
        when(mock.getSlot()).thenReturn(0);
        when(mock.getClick()).thenReturn(LEFT);

        menu.addButton(testButton);
        menu.getButton(0).getOnClick().accept(mock);
        assertEquals("clicked", log.toString());
    }


    @Test
    public void testMenu_pageAdded_whenFull() {
        for (int i = 0; i < 10; i++) {
            menu.addButton(testButton);
        }
        assertEquals(MenuButtons.NEXT_PAGE, menu.getButton(8));
        assertEquals(2, menu.getPages().size());
    }

    @Test
    public void testMenu_pageAdded_retainsButtons() {
        List<MenuButton> buttons = IntStream.range(0, 10).mapToObj(i -> new MenuButtonBuilder().setType(Material.STONE).setName(String.valueOf(i)).buildButton()).collect(Collectors.toList());
        for (MenuButton button : buttons) {
            menu.addButton(button);
        }
        List<MenuButton> allButtons = menu.getAllButtons();
        assertEquals(buttons.get(0), allButtons.get(0));
        assertEquals(buttons.get(1), allButtons.get(1));
        assertEquals(buttons.get(2), allButtons.get(2));
        assertEquals(buttons.get(3), allButtons.get(3));
        assertEquals(buttons.get(4), allButtons.get(4));
        assertEquals(buttons.get(5), allButtons.get(5));
        assertEquals(buttons.get(6), allButtons.get(6));
        assertEquals(buttons.get(7), allButtons.get(7));
        assertEquals(buttons.get(8), allButtons.get(10));
    }

    @Test
    public void testMenu_nextPageTakesToNextPage() {
        for (int i = 0; i < 10; i++) {
            menu.addButton(testButton);
        }
        PlayerMock player = server.addPlayer();
        MenuClickEvent mock = Mockito.mock(MenuClickEvent.class);
        when(mock.getSlot()).thenReturn(8);
        when(mock.getClick()).thenReturn(LEFT);
        when(mock.getMenu()).thenReturn(menu);
        when(mock.getWhoClicked()).thenReturn(player);
        menu.getButton(8).getOnClick().accept(mock);

        assertEquals(MenuButtons.PREVIOUS_PAGE, ((MenuHolder) player.getOpenInventory().getTopInventory().getHolder()).getMenu().getButton(0));
    }

    @Test
    public void testMenu_previousPageTakesToPreviousPage() {
        for (int i = 0; i < 10; i++) {
            menu.addButton(testButton);
        }
        PlayerMock player = server.addPlayer();
        MenuClickEvent mock = Mockito.mock(MenuClickEvent.class);
        when(mock.getSlot()).thenReturn(8);
        when(mock.getClick()).thenReturn(LEFT);
        when(mock.getMenu()).thenReturn(menu.getPages().get(1));
        when(mock.getWhoClicked()).thenReturn(player);

        MenuButton previousButton = menu.getPages().get(1).getButton(0);
        previousButton.getOnClick().accept(mock);

        MenuHolder holder = (MenuHolder) player.getOpenInventory().getTopInventory().getHolder();
        assertEquals(MenuButtons.NEXT_PAGE, holder.getMenu().getButton(8));
    }


}
