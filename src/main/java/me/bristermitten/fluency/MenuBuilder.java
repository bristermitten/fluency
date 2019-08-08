package me.bristermitten.fluency;

import me.bristermitten.fluency.button.MenuButtonBuilder;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;

public interface MenuBuilder {
    MenuBuilder distributed(ButtonDistribution distribution);

    MenuBuilder title(String title);

    MenuBuilder size(int size);

    MenuButtonBuilder buildButton();
    
}
