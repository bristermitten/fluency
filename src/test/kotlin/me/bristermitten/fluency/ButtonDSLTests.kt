package me.bristermitten.fluency

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ButtonDSLTests {
    private lateinit var fluency: Fluency

    @Before
    fun init() {
        BukkitMock.init()
        fluency = Fluency.create(null)
    }

    @Test
    fun `Test Enchantments Properly Added`() {
        val button = fluency.createButton {
            type = Material.STONE

            enchants[Enchantment.DIG_SPEED] = 2
            enchants[Enchantment.KNOCKBACK] = 2
        }

        assertEquals(button.getEnchantmentLevel(Enchantment.DIG_SPEED), 2)
        assertEquals(button.getEnchantmentLevel(Enchantment.KNOCKBACK), 2)
        assertEquals(button.getEnchantmentLevel(Enchantment.ARROW_DAMAGE), 0)
    }
}
