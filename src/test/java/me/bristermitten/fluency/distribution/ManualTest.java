package me.bristermitten.fluency.distribution;

import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.distribution.ManualButtonDistribution;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ManualTest {
    private ButtonDistribution distribution;
    private Iterable<Integer> slots;

    @Before
    public void init() {
        slots = Arrays.asList(1, 2, 3, 4, 5, 6);
        distribution = new ManualButtonDistribution(slots);
    }

    @Test
    public void testBasicIteration() {
        for (int slot : slots) {
            assertEquals(slot, distribution.nextSlot());
        }
    }

}
