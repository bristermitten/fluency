package me.bristermitten.fluency.distribution;

import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.distribution.DistributionNotInitialisedException;
import me.bristermitten.fluency.button.distribution.SimpleButtonDistribution;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ButtonDistributionTests {
    private static int size;
    private ButtonDistribution distribution;

    @BeforeClass
    public static void initClass() {
        size = 27;
    }

    @Before
    public void init() {
        distribution = new SimpleButtonDistribution();
    }

    @Test(expected = DistributionNotInitialisedException.class)
    public void testDistribution_notInitialised_throwsException() {
        distribution.nextSlot();
    }

    @Test
    public void testButtonDistribution_next() {
        distribution.init(size);
        assertEquals(0, distribution.nextSlot());
        distribution.skip();
        assertEquals(3, distribution.nextSlot());
    }

    @Test
    public void testButtonDistribution_copy() {
        distribution.init(size);
        assertEquals(distribution, distribution.copy());
    }

    @Test
    public void testToArray() {
        distribution.init(size);
        int[][] a = distribution.toArray();
        List<Integer> numbers = IntStream.range(0, 27).boxed().collect(Collectors.toList());
        assertTrue(Arrays.stream(a).flatMapToInt(Arrays::stream).distinct().allMatch(numbers::contains));
    }
}
