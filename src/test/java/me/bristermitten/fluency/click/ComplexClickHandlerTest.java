package me.bristermitten.fluency.click;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.click.MenuClickEvent;
import org.bukkit.event.inventory.ClickType;
import org.junit.Before;
import org.junit.Test;

import static org.bukkit.event.inventory.ClickType.RIGHT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComplexClickHandlerTest {

    private Fluency fluency;

    @Before
    public void init() {
        BukkitMock.init();
        fluency = Fluency.create(null);
    }


    @Test
    public void testComplexClickHandling_1() {
        StringBuilder builder = new StringBuilder();
        MenuButton button = fluency.buildButton()
                .onClick()
                .when(true)
                .action((e) -> builder.append(true))
                .otherwise().action(e -> builder.append(false))
                .done().build();

        MenuClickEvent mock = mock(MenuClickEvent.class);
        button.handler().accept(mock);


        assertEquals(Boolean.TRUE.toString(), builder.toString());
    }

    @Test
    public void testComplexClickHandling_2() {
        StringBuilder builder = new StringBuilder();
        MenuButton button = fluency.buildButton()
                .onClick(ClickType.LEFT)
                .when(true)
                .action((e) -> builder.append(true))
                .otherwise()
                .action(e -> builder.append(false))
                .done().build();

        MenuClickEvent mock = mock(MenuClickEvent.class);
        when(mock.getClick()).thenReturn(ClickType.LEFT);
        button.handler().accept(mock);


        assertEquals(Boolean.TRUE.toString(), builder.toString());
    }

    @Test
    public void testComplexClickHandling_3() {
        StringBuilder builder = new StringBuilder();
        MenuButton button = fluency.buildButton()
                .onClick(ClickType.LEFT)
                .when(true)
                .action((e) -> builder.append(true))
                .whenClickType(RIGHT)
                .action(e -> builder.append(false))
                .done().build();

        MenuClickEvent mock = mock(MenuClickEvent.class);
        when(mock.getClick()).thenReturn(RIGHT);
        button.handler().accept(mock);


        assertEquals(Boolean.FALSE.toString(), builder.toString());
    }
}
