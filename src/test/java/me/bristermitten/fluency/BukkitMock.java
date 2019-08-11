package me.bristermitten.fluency;

import be.seeseemelk.mockbukkit.MockBukkit;

public class BukkitMock {

    public static void init() {
        if (!MockBukkit.isMocked())
            MockBukkit.mock();
    }

    public static void stop() {
        MockBukkit.unload();
    }
}
