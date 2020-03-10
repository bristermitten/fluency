package me.bristermitten.fluency.button.template;

import me.bristermitten.fluency.BukkitMock;
import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.SimpleDataClass;
import me.bristermitten.fluency.menu.Menu;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


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

		for (int i = 0; i < 3; i++) {
			assertEquals(
					fluency.buildButton()
							.type(Material.STONE).name(String.valueOf(i + 1))
							.build(),
					build.button(i));
		}
	}
}
