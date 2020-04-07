package me.bristermitten.fluency.button.distribution;

import java.util.function.Supplier;

/**
 * Determines how buttons are distributed
 * This does away with direct slot setting in favour of a more abstract approach.
 * A {@link ButtonDistribution} defines the order that buttons are placed onto a Menu.
 * They should respect the size of the menu, and expect {@link ButtonDistribution#init(int)} and {@link ButtonDistribution#copy()} to be called
 * frequently.
 *
 * @apiNote Implementations of this class should appropriately override {@link ButtonDistribution#copy()}
 */
public interface ButtonDistribution extends Iterable<Integer> {

    /**
     * Iterates in direct order
     */
    Supplier<ButtonDistribution> simple = SimpleButtonDistribution::new;
    /**
     * Iterates down in columns rather than rows
     */
    Supplier<ButtonDistribution> columns = ColumnButtonDistribution::new;

    /**
     * Iterates starting from the center and expanding outwards
     */
    Supplier<ButtonDistribution> centered = CenteredButtonDistribution::new;

    /**
     * Initialize the distribution with the size of the menu it must distribute buttons for
     * This method must be called before any others
     */
    void init(int size);

    /**
     * Skip one or many slots. This is difficult to describe as different implementations are driven by this method.
     * General contract is that 1 call should change the internal index by at least 1,
     * being functionally equivalent to adding an empty button
     * For example, in {@link SimpleButtonDistribution}, skip() results in this (simplified for clarity)
     * Key: + = selected slot, - = empty slot, * = filled slot
     * [+][-][-][-] becomes
     * [-][+][][-]
     */
    void skip();

    boolean hasNext();

    int currentSlot();

    int nextSlot();

    /**
     * Create a copy of this distribution
     * Note that this will copy over initialization status and size, but not current index, which will be reset
     *
     * <p>
     * In {@link AbstractButtonDistribution}, reflection is used to initialize subclasses, and so
     * implementations are recommended to override this method with a constructor
     *
     * @return a copy of this distribution
     */
    ButtonDistribution copy();

    /**
     * Create a multidimensional array representing how a menu would look when using this distribution.
     * Depending on the implementation, this method may consume all the indexes in the distribution,
     * so should only be used for debugging.
     * <p>
     * The array format follows a simple coordinate system. For example, toArray()[1][3]
     * returns the 4th element in the 2nd row.
     * The values are the order that they were placed into the array by the distribution to help
     * visualise the output of one. For example, in {@link SimpleButtonDistribution}
     * the array is ordered as [0,1,2,3,4] etc.
     *
     * @return a multidimensional array representing the output of this distribution
     */
    int[][] toArray();


}
