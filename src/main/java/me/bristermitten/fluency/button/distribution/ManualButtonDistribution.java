package me.bristermitten.fluency.button.distribution;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Manually iterates over a list of slots
 * Used when manual control is necessary
 */
public class ManualButtonDistribution extends AbstractButtonDistribution {
    private Iterator<Integer> iterator;
    private Iterable<Integer> origIter;
    private Iterator<Integer> original;
    private boolean cachedIndex = false;

    public ManualButtonDistribution(Integer... slots) {
        this(Arrays.asList(slots));
    }

    public ManualButtonDistribution(Iterator<Integer> iterator) {
        this.iterator = iterator;
    }

    public ManualButtonDistribution(Iterable<Integer> slots) {
        this.iterator = slots.iterator();
        this.original = slots.iterator();
        this.origIter = slots;
    }

    private ManualButtonDistribution() {

    }

    @Override
    public void skip() {
        index = iterator.next();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public int nextSlot() {
        if (cachedIndex) {
            cachedIndex = false;
            return index;
        }
        return index = iterator.next();
    }

    @Override
    public void init(int size) {
        super.init(size);
        index = nextSlot();
        cachedIndex = true;
    }

    @Override
    public ButtonDistribution copy() {
        ManualButtonDistribution integers = new ManualButtonDistribution();
        if (origIter != null) {
            return new ManualButtonDistribution(origIter);
        }
        if (original != null) {
            return new ManualButtonDistribution(original);
        }
        return integers;
    }
}
