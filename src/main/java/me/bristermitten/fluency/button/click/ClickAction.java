package me.bristermitten.fluency.button.click;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ClickAction implements ClickHandler {
    private List<Predicate<MenuClickEvent>> conditions = new ArrayList<>();
    private List<ClickHandler> run = new ArrayList<>();

    public void addCondition(Predicate<MenuClickEvent> condition) {
        conditions.add(condition);
    }

    public void addRun(ClickHandler run) {
        this.run.add(run);
    }

    @Override
    public void accept(MenuClickEvent e) {
        for (Predicate<MenuClickEvent> condition : conditions) {
            boolean test = condition.test(e);
            if (!test) return;
        }
        run.forEach(h -> h.accept(e));
    }

    public ClickAction copySwapConditions() {
        ClickAction copy = new ClickAction();
        conditions.stream().map(Predicate::negate).forEach(copy::addCondition);
        return copy;
    }

    @Override
    public String toString() {
        return "ClickAction{" +
                "conditions=" + conditions +
                ", run=" + run +
                '}';
    }
}
