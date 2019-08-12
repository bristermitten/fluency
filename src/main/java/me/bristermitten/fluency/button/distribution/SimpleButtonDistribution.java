package me.bristermitten.fluency.button.distribution;

public class SimpleButtonDistribution extends AbstractButtonDistribution {
    @Override
    public ButtonDistribution copy() {
        SimpleButtonDistribution s = new SimpleButtonDistribution();
        if (isInit()) {
            s.init(maxSize);
        }
        return s;
    }
}
