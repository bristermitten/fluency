package me.bristermitten.fluency.jui;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.menu.Menu;
import me.bristermitten.fluency.menu.MenuBuilder;
import me.bristermitten.jui.ui.UI;
import me.bristermitten.jui.ui.UIRenderer;
import me.bristermitten.reflector.property.Element;

public class FluencyUIRenderer implements UIRenderer<Menu, Object> {
    private final Fluency fluency;

    public FluencyUIRenderer(Fluency fluency) {
        this.fluency = fluency;
    }

    @Override
    public Menu renderGraphicalUI(UI ui) throws UnsupportedOperationException {
        MenuBuilder menuBuilder = fluency.buildMenu();
        menuBuilder.title(ui.getTitle());
        menuBuilder.size(ui.getSize());
        for (Element element : ui.getElements()) {
            addProperties(element, menuBuilder);
        }
        return menuBuilder.build();
    }

    private void addProperties(Element e, MenuBuilder builder) {
        ButtonBuilder buttonBuilder = builder.buildButton();
        buttonBuilder.name(e.getName());

        if (e.isComplexType()) {
            buttonBuilder.onClick().openMenu(forElement(e));
        }
        else {
            buttonBuilder.lore("&c&lValue: &7");
        }
    }

    private Menu forElement(Element e) {
        return fluency.buildMenu().build();
    }

    @Override
    public Object renderTextUI(UI ui) throws UnsupportedOperationException {
        return null;
    }
}
