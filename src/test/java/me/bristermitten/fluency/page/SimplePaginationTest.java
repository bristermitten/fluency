package me.bristermitten.fluency.page;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.menu.Menu;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SimplePaginationTest {
    private Fluency fluency;

    @Before
    public void init() {
        BukkitMock.init();
        fluency = Fluency.create(null);
    }

    @Test
    public void testPageAdded() {
        MenuBuilder menuBuilder = fluency.buildMenu().size(9);
        IntStream.range(0, 10).forEach(i -> menuBuilder.buildButton().type(Material.STONE).amount(i));
        Menu build = menuBuilder.build();
        assertEquals(2, build.pages().size());
    }
}
