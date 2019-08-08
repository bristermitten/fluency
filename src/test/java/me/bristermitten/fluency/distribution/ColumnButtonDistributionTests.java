package me.bristermitten.fluency.distribution;

import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.distribution.ColumnButtonDistribution;
import me.bristermitten.fluency.button.distribution.DistributionNotInitialisedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ColumnButtonDistributionTests {
    private static int size;
    private ButtonDistribution distribution;

    @BeforeClass
    public static void initClass() {
        size = 27;
    }

    @Before
    public void init() {
        distribution = new ColumnButtonDistribution();
    }

    @Test(expected = DistributionNotInitialisedException.class)
    public void testDistribution_notInitialised_throwsException() {
        distribution.nextSlot();
    }

    @Test
    public void testColumnButtonDistribution_next() {
        distribution.init(size);
        assertEquals(0, distribution.nextSlot());
        distribution.skip();
        assertEquals(2, distribution.currentSlot());
    }

    @Test
    public void testSimpleButtonDistribution_copy() {
        distribution.init(size);
        assertEquals(distribution, distribution.copy());
    }
    @Test
    public void testToArray() {
        distribution.init(size);
        int[][] a = distribution.toArray();
        StringBuilder sb = new StringBuilder();
        for(int[] s1 : a){
            sb.append(Arrays.toString(s1)).append('\n');
        }
        String s = sb.toString();
        System.out.println(s);
    }
}
