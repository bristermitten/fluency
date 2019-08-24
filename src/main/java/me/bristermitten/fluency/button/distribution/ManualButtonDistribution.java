package me.bristermitten.fluency.button.distribution;

import java.util.Iterator;

/**
 * Manually iterates over a list of slots
 * Used when manual control is necessary
 */
public class ManualButtonDistribution extends AbstractButtonDistribution {
    private Iterator<Integer> iterator;
    private Iterator<Integer> original;

    private int current;

    public ManualButtonDistribution(Iterator<Integer> iterator) {
        this.iterator = iterator;
    }

    public ManualButtonDistribution(Iterable<Integer> slots) {
        this.iterator = slots.iterator();
        this.original = slots.iterator();
    }

    private ManualButtonDistribution() {

    }

    @Override
    public void skip() {
        current = iterator.next();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public int nextSlot() {
        return current = iterator.next();
    }

    @Override
    public int currentSlot() {
        return current;
    }

    @Override
    public ButtonDistribution copy() {
        ManualButtonDistribution integers = new ManualButtonDistribution();
        if (isInit() && original != null) {
            integers = new ManualButtonDistribution(original);
        }
        return integers;
    }
}
