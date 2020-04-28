package me.bristermitten.fluency.distribution;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.distribution.ManualButtonDistribution;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ManualTest {
    private ButtonDistribution distribution;
    private Iterable<Integer> slots;
    private Fluency fluency;

    @Before
    public void init() {
        BukkitMock.init();
        slots = Arrays.asList(1, 2, 3, 4, 5, 6);
        distribution = new ManualButtonDistribution(slots);
        fluency = Fluency.create(null);
    }

    @Test
    public void testBasicIteration() {
        for (int slot : slots) {
            assertEquals(slot, distribution.nextSlot());
        }
    }

    @Test
    public void testToArray() {
        distribution.init(9);
        int[][] ints = distribution.toArray();
        assertArrayEquals(new int[]{-1, 0, 1, 2, 3, 4, 5, -1, -1}, ints[0]);
    }

    @Test
    public void testFluentDistribution() {
        MenuBuilder builder = fluency.buildMenu()
                .size(3 * 9).title("Test Menu")
                .distributed(distribution);
    }
}
