package me.bristermitten.fluency.click;

import me.bristermitten.fluency.button.click.Handlers;
import me.bristermitten.fluency.button.click.MenuClickEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClickHandlerTests {

    @Test
    public void testSimpleCancel() {
        MenuClickEvent e = Mockito.mock(MenuClickEvent.class);
        Handlers.CANCEL.accept(e);
        verify(e).setCancelled(true);
    }
}
