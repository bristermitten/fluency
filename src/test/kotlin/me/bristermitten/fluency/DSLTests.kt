package me.bristermitten.fluency

import be.seeseemelk.mockbukkit.MockBukkit
import me.bristermitten.fluency.button.MenuButton
import me.bristermitten.fluency.button.click.MenuClickEvent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DSLTests {
    private lateinit var fluency: Fluency

    @Before
    fun setUp() {
        BukkitMock.init()
        fluency = Fluency.create(null)
    }

    @Test
    fun `Test DSL Building`() {
        val loreString = "Background Lore"
        val menu = fluency.createMenu {
            title = "Menu"
            size = 36

            background {
                type = Material.BLACK_STAINED_GLASS_PANE

                name = "Background"
                lore += loreString
                onClick {
                    cancel()

                    action {
                        health = 0.0
                        sendMessage("You died for clicking on the background!")
                    }

                    whenPlayer(Player::isOp) {
                        sendMessage("You are OP!")
                    }
                    otherwise {
                        sendMessage("You are not OP!")
                    }
                }
            }
        }

        val background = menu.background().get()
        assertNotNull(background)


        assertEquals(Material.BLACK_STAINED_GLASS_PANE, background.type)


        val event = mock(MenuClickEvent::class.java)
        val player = MockBukkit.getMock().addPlayer()
        `when`(event.clicker()).thenReturn(player)

        background.handler().accept(event)

        assertEquals(0.0, player.health)
        assertEquals("You died for clicking on the background!", player.nextMessage())
        assertEquals("You are not OP!", player.nextMessage())
        assertEquals(loreString, background.itemMeta?.lore?.get(0))
    }

    @Test
    fun `Test DSL Templates`() {
        val data = SimpleDataClass(30, "John Smith", null)
        val menu = fluency.createMenu {
            title = "Menu"
            size = 36

            addTemplate<SimpleDataClass> {
                type = Material.STONE

                name {
                    it.name
                }

                loreString {
                    """
						Age: ${it.age}
						Parent: ${it.parent?.name}
					""".trimIndent()
                }

                source(data)
            }
        }

        val button = menu.button(0)
        assertEquals(Material.STONE, button.type)
        assertEquals("John Smith", button.itemMeta?.displayName)
    }

    @Test
    fun `Test DSL Player Templates`() {
        val menu = fluency.createMenu {
            title = "Menu"
            size = 36

            addPlayerTemplate {
                type = Material.STONE

                name {
                    it.name
                }

                loreString {
                    "${it.health}"
                }

                onClick {
                    whenPlayer(Player::isOp) {
                        sendMessage("You're OP")
                    }
                    otherwise {
                        sendMessage("Boo...")
                    }
                }
            }
        }
        val player = MockBukkit.getMock().addPlayer()
        menu.open(player)

        val button = player.openInventory.topInventory.getItem(0)!!
        assertEquals(Material.STONE, button.type)
        assertEquals(player.name, button.itemMeta?.displayName)
        assertEquals(player.health.toString(), button.itemMeta?.lore?.get(0))

        val event = mock(MenuClickEvent::class.java)
        `when`(event.clicker()).thenReturn(player)

        (button as MenuButton).handler().accept(event)
        assertEquals("Boo...", player.nextMessage())
    }

    @Test
    fun `Test DSL Building Button`() {
        val button = fluency.createButton {
            type = Material.STONE
            name = "Hello"
            amount = 3
            loreString("""
                This is a Test
                Button
            """.trimIndent())
        }

        assertEquals(Material.STONE, button.type)
        assertEquals("Hello", button.itemMeta?.displayName)
        assertEquals(3, button.amount)
        assertEquals("This is a Test", button.itemMeta?.lore?.get(0))
        assertEquals("Button", button.itemMeta?.lore?.get(1))
    }
}
