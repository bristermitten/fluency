package me.bristermitten.fluency.click;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.click.ClickHandler;
import me.bristermitten.fluency.button.click.MenuClickEvent;
import org.bukkit.event.inventory.ClickType;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;

import static org.bukkit.event.inventory.ClickType.LEFT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClickBuilderTests {
    private Fluency fluency;

    @Before
    public void init() {
        BukkitMock.init();
        fluency = Fluency.create(null);
    }

    @Test
    public void testTwoHandlers() {
        ButtonBuilder buttonBuilder = fluency.buildButton();
        StringWriter writer = new StringWriter();
        buttonBuilder.onClick().action(e -> writer.write(1)).done();
        buttonBuilder.onClick(ClickType.RIGHT).action(e -> writer.write(2));

        MenuClickEvent mock = mock(MenuClickEvent.class);
        when(mock.getClick()).thenReturn(ClickType.RIGHT);

        ClickHandler handler = buttonBuilder.build().handler();
        handler.accept(mock);

        assertEquals(String.valueOf(new char[]{(char) 1, (char) 2}), writer.toString());

        writer.getBuffer().delete(0, writer.getBuffer().capacity());

        mock = mock(MenuClickEvent.class);
        when(mock.getClick()).thenReturn(LEFT);
        handler.accept(mock);

        assertEquals(String.valueOf((char) 1), writer.toString());
    }
}
