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
            if (!condition.test(e)) return;
        }
        run.forEach(h -> h.accept(e));
    }

    public ClickAction copySwapConditions() {
        ClickAction clickAction = new ClickAction();
        conditions.stream().map(Predicate::negate).forEach(p -> clickAction.conditions.add(p));
        clickAction.run.addAll(run);
        return clickAction;
    }
}
