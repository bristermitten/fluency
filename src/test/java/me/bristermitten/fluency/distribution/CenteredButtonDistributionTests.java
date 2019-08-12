package me.bristermitten.fluency.distribution;

import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.distribution.CenteredButtonDistribution;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CenteredButtonDistributionTests {
    private static int size;
    private ButtonDistribution distribution;

    @BeforeClass
    public static void initClass() {
        size = 9;
    }

    @Before
    public void init() {
        distribution = new CenteredButtonDistribution();
    }


    @Test
    public void testSimpleStartCenter() {
        distribution.init(size);
        assertEquals(4, distribution.nextSlot());
        int[][] array = distribution.toArray();
        List<Integer> numbers = IntStream.range(0, 27).boxed().collect(Collectors.toList());
        assertTrue(Arrays.stream(array).flatMapToInt(Arrays::stream).distinct().allMatch(numbers::contains));
    }
}
