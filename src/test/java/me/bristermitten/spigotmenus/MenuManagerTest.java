package me.bristermitten.spigotmenus;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.Before;

public class MenuManagerTest {
    private ServerMock server;
    private SpigotMenusTestPlugin plugin;

    @Before
    public void init() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(SpigotMenusTestPlugin.class);
    }
}
