package me.bristermitten.fluency;

import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.click.MenuClickEvent;
import me.bristermitten.fluency.button.distribution.ManualButtonDistribution;
import me.bristermitten.fluency.menu.Menu;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.bukkit.Material.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MenuBuilderTests {
    private Fluency fluency;

    @Before
    public void init() {
        BukkitMock.init();
        fluency = Fluency.create(null);
    }

    @After
    public void tearDown() {
        BukkitMock.stop();
    }

    @Test()
    public void testSimpleBuild() {
        fluency.buildMenu().build();
    }

    @Test
    public void testAddButton() {
        Menu build = fluency.buildMenu()
                .buildButton().done()
                .build();
        MenuButton button = build.button(0);
        assertEquals(AIR, button.getType());
        assertNull(build.button(1));
    }

    @Test
    public void testAddButtonWithData() {
        Menu build = fluency.buildMenu()
                .buildButton()
                .amount(30).type(STONE)
                .done()
                .build();
        assertEquals(STONE, build.button(0).getType());
        assertEquals(30, build.button(0).getAmount());
    }

    @Test
    public void testChaining() {
        Menu build = fluency.buildMenu().size(36).title("Test Menu")
                .buildButton().type(STONE).amount(12).onClick().sendMessage("Clicked").done().done()
                .build();

        MenuClickEvent e = mock(MenuClickEvent.class);

        Player p = mock(Player.class);
        when(e.getWhoClicked()).thenReturn(p);
        build.button(0).handler().accept(e);
        Mockito.verify(p).sendMessage("Clicked");
        when(p.openInventory(any(Inventory.class))).then(a -> {
            Object argument = a.getArguments()[0];
            Inventory i = (Inventory) argument;
            assertEquals(STONE, i.getItem(0).getType());
            return null;
        });
        build.open(p);
    }

    @Test
    public void testBackground() {
        MenuBuilder builder = fluency.buildMenu().size(36).title("Test Menu")
                .buildButton().type(STONE).amount(12).onClick().sendMessage("Clicked").done().done();
        MenuButton background = builder.buildBackground().type(REDSTONE).onClick().cancel().done().build();

        Menu menu = builder.build();

        MenuClickEvent e = mock(MenuClickEvent.class);
        assertEquals(background, menu.button(3));
    }

    @Test
    public void testBackground2() {
        MenuBuilder builder = fluency.buildMenu().size(36).title("Test Menu")
                .distributed(new ManualButtonDistribution(1))
                .buildButton().type(STONE).amount(12).onClick().sendMessage("Clicked").done().done();
        MenuButton background = builder.buildBackground().type(REDSTONE).onClick().cancel().done().build();

        Menu menu = builder.build();

        assertEquals(background, menu.background().get());

        for (int i = 0; i < 36; i++) {
            if(i == 1)continue;
            assertEquals(background, menu.button(i));
        }

        Player p = mock(Player.class);
        when(p.openInventory(any(Inventory.class))).then(invocation -> {
            Inventory argument = (Inventory) invocation.getArguments()[0];

            for (ItemStack itemStack : argument) {
                System.out.println(itemStack);
            }
            return null;
        });
        menu.open(p);
    }

    @Test
    public void testAddPredefinedButton() {
        MenuButton build = fluency.buildButton().type(STONE).build();
        Menu menu = fluency.buildMenu().size(9).title("Test Menu")
                .addButton(build).build();

        assertEquals(build, menu.button(0));

        menu.addPage();
        assertEquals(Fluency.PAGE_NEXT, menu.button(8));

//        MenuClickEvent mock = mock(MenuClickEvent.class);
//        when(mock.menu()).thenReturn(menu);
//        Handlers.NEXT_PAGE.accept(mock);
    }
}
