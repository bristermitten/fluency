package me.bristermitten.fluency.button.distribution;

public class ColumnButtonDistribution extends AbstractButtonDistribution {
    private int currentRow = 1;

    @Override
    public void init(int size) {
        super.init(size);
        index = -MENU_WIDTH; //so we get 0 on our first nextSlot();
    }

    @Override
    public void skip() {
        super.skip();
        index %= MENU_WIDTH;
    }

    /*
        [0][1][2][3][4][5][6][7][8]
        [9][10][11][12][13][14][15][16][17]     size = 27
        [18][19][20][21][22][23][24][25][26]
         */
    @Override
    public int nextSlot() {
        checkInit();
        if (index >= maxSize - MENU_WIDTH) {
            index++;
            return index %= MENU_WIDTH;
        }
        return index += MENU_WIDTH;
    }

    @Override
    public boolean hasNext() {
        return index < maxSize - 1;
    }
}
