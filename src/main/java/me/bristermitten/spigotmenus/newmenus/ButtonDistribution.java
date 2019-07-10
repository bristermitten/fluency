package me.bristermitten.spigotmenus.newmenus;

import java.util.function.Function;

/**
 * Determines how buttons are distributed
 * Essentially, used for iterating over the slots in a menu, however not necessarily in order
 */
public interface ButtonDistribution {

    /**
     * Iterates over slots in order, ranging from 0, to the maximum size -1
     */
    ButtonDistribution ROWS =(Function<Integer, ButtonDistribution>) size -> new AbstractButtonDistribution(size) {
    };

    void skip();
}
