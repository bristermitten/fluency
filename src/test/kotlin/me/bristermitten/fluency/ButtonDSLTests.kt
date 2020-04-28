package me.bristermitten.fluency

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ButtonDSLTests {
    private lateinit var fluency: Fluency


    @Before
    fun setUp() {
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

        assertEquals(2, button.getEnchantmentLevel(Enchantment.DIG_SPEED))
        assertEquals(2, button.getEnchantmentLevel(Enchantment.KNOCKBACK))
        assertEquals(0, button.getEnchantmentLevel(Enchantment.ARROW_DAMAGE))
    }

    @Test
    fun `Test Flags Properly Added`() {
        val button = fluency.createButton {
            type = Material.STONE

            flags.add(ItemFlag.HIDE_ENCHANTS)
        }

        assertEquals(true, button.itemMeta?.hasItemFlag(ItemFlag.HIDE_ENCHANTS))
        assertEquals(false, button.itemMeta?.hasItemFlag(ItemFlag.HIDE_DESTROYS))
    }
}
