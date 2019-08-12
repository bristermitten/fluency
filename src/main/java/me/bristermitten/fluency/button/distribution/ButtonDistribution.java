package me.bristermitten.fluency.button.distribution;

import java.util.function.Supplier;

/**
 * Determines how buttons are distributed
 * Essentially, used for iterating over the slots in a menu, however not necessarily in order
 */
public interface ButtonDistribution extends Iterable<Integer> {

    /**
     * Iterates in direct order
     */
    Supplier<ButtonDistribution> SIMPLE = SimpleButtonDistribution::new;
    /**
     * Iterates down in columns rather than rows
     */
    Supplier<ButtonDistribution> COLUMNS = ColumnButtonDistribution::new;

    /**
     * Initialize the distribution with the size of the menu it must distribute buttons for
     * This method must be called before any others
     */
    void init(int size);

    /**
     * Skip one or many slots. This is difficult to describe as different implementations are driven by this method.
     * General contract is that 1 call should change the internal index by at least 1, and cause a gap between slots of at least 1
     * For example, in {@link AbstractButtonDistribution}, skip() results in this (simplified for clarity)
     * Key: + = selected slot, - =empty slot, * = filled slot
     * [+][-][-][-] becoming
     * [-][-][+][-]
     */
    void skip();

    boolean hasNext();

    int currentSlot();

    int nextSlot();

    /**
     * Create a copy of this distribution
     * Note that this will copy over initialization status, but not current index
     * <p>
     * In {@link AbstractButtonDistribution}, reflection is used to initialize subclasses, and so
     * implementations are recommended to override this method with a constructor
     *
     * @return a copy of this distribution
     */
    ButtonDistribution copy();

    int[][] toArray();


}
