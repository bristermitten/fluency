package me.bristermitten.fluency.button.distribution;

/**
 * Buttons start being placed at the center, and expanding outwards in all directions, the x axis prioritised over y
 * It's recommended to use a menu with an odd number of rows for a true center, otherwise 2 rows will be used
 * <p>
 * The placement of buttons from the middle is constant - left is prioritised.
 * That is, if 2 buttons were added, the placement will be [0][0][0][2][1][0][0][0][0].
 * When the third is added it will become [0][0][0][2][1][3][0][0][0]
 * <p>
 * You're recommended to extend this class to customise functionality if necessary
 */

public class CenteredButtonDistribution extends AbstractButtonDistribution {
    /**
     * essentially the absolute value of the slot we're currently on, relative to the center slot
     */
    protected int distance;

    protected Direction direction;

    protected int centerSlot;

    protected int rowsFilled;

    protected int toggleCount;

    public void init(int size) {
        super.init(size);
        this.index = this.centerSlot = (MENU_WIDTH * ((height + 1) / 2) - 1) / 2;
        distance = 1;
    }

    @Override
    public int nextSlot() {
        if (direction == null) {
            direction = Direction.LEFT;
            return index;
        }
        index = direction.move(centerSlot, distance);
        direction = direction.inverse();
        toggleCount++;
        if (toggleCount >= 2) {
            distance++;
            toggleCount = 0;
        }
        return index;
    }

    @Override
    public void skip() {
        distance++;
    }

    @Override
    public boolean hasNext() {
        //benefit of starting distance from the middle, if the distance from the middle to the edge
        //is more than half the size it must be out of bounds
        return distance <= (maxSize / 2);
    }


    @Override
    public ButtonDistribution copy() {
        CenteredButtonDistribution b = new CenteredButtonDistribution();
        if (isInit()) {
            b.init(maxSize);
        }
        return b;
    }

    protected enum Direction {
        LEFT {
            @Override
            public int move(int current, int mod) {
                return current - mod;
            }

            @Override
            public Direction inverse() {
                return RIGHT;
            }
        },
        RIGHT {
            @Override
            public int move(int current, int mod) {
                return current + mod;
            }

            @Override
            public Direction inverse() {
                return LEFT;
            }
        };

        public abstract int move(int current, int mod);

        public abstract Direction inverse();
    }
}
