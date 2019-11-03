package me.bristermitten.fluency.button.template;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.menu.Menu;
import me.bristermitten.fluency.menu.MenuBuilder;
import me.bristermitten.reflector.examples.simple.SimpleDataClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ButtonTemplateTests {
    private Fluency fluency;

    @Before
    public void setUp() {
        BukkitMock.init();
        fluency = Fluency.create(null);
    }

    @Test
    public void testSimpleFunctionality() {

        List<SimpleDataClass> dataClassList = new ArrayList<>();
        dataClassList.add(new SimpleDataClass(3, "1", null));
        dataClassList.add(new SimpleDataClass(4, "2", null));
        dataClassList.add(new SimpleDataClass(5, "3", null));

        MenuBuilder menuBuilder = fluency.buildMenu()
                .buildTemplatesForEach(dataClassList)
                .nameFrom(SimpleDataClass::getName)
                .done();
        Menu build = menuBuilder.build();

        System.out.println(build.button(0));
        System.out.println(build.button(1));
        System.out.println(build.button(0));
        System.out.println(build.button(1));
        System.out.println(build.button(2));
    }
}
