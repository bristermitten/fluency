package me.bristermitten.fluency.button.distribution;


import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractButtonDistribution implements ButtonDistribution {
    protected static final int MENU_WIDTH = 9;
    protected int maxSize = -1;
    protected int index = 0;
    protected int height;

    @Override
    public void init(int size) {
        Validate.isTrue(size > 0, "Size is less than 0");
        Validate.isTrue(size % MENU_WIDTH == 0, "Size is not a multiple of 9");
        this.maxSize = size;
        this.height = size / MENU_WIDTH;
        this.index = 0;
    }

    @Override
    public void skip() {
        checkInit();
        index += 2;
    }

    @Override
    public boolean hasNext() {
        checkInit();
        return index < maxSize;
    }

    @Override
    public int currentSlot() {
        return index;
    }

    @Override
    public int nextSlot() {
        checkInit();
        return index++;
    }

    protected void checkInit() {
        if (maxSize == -1) throw new DistributionNotInitialisedException();
    }

    @Override
    public ButtonDistribution copy() {
        try {
            AbstractButtonDistribution d = getClass().newInstance();
            d.init(maxSize);
            return d;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractButtonDistribution that = (AbstractButtonDistribution) o;
        return maxSize == that.maxSize && index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxSize, index);
    }

    @Override
    public int[][] toArray() {
        checkInit();
        ButtonDistribution copy = copy();

        int[] arr = new int[maxSize];
        Arrays.fill(arr, -1);
        int x = 0;
        while (copy.hasNext()) {
            int i = copy.nextSlot();
            arr[i] = x++;
        }
        int[][] arr2 = new int[height][MENU_WIDTH];
        for (int i = 0; i < height; i++) {
            System.arraycopy(arr, i * MENU_WIDTH, arr2[i], 0, MENU_WIDTH);
        }
        return arr2;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return AbstractButtonDistribution.this.hasNext();
            }

            @Override
            public Integer next() {
                return AbstractButtonDistribution.this.nextSlot();
            }
        };
    }
}
