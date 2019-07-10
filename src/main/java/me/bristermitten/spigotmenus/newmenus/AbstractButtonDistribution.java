package me.bristermitten.spigotmenus.newmenus;

public class AbstractButtonDistribution implements ButtonDistribution {
    private final int maxSize;

    public AbstractButtonDistribution(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void skip() {

    }
}
