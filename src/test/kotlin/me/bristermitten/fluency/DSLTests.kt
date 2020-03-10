package me.bristermitten.fluency

import be.seeseemelk.mockbukkit.MockBukkit
import me.bristermitten.fluency.button.click.MenuClickEvent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DSLTests {
	private lateinit var fluency: Fluency

	@Before
	fun init() {
		BukkitMock.init()
		fluency = Fluency.create(null)
	}

	@Test
	fun testBuilding() {
		val menu = fluency.createMenu {
			title = "Menu"
			size = 36

			background {
				type = Material.STAINED_GLASS_PANE
				data = 7

				name = "Background"
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


		assertEquals(Material.STAINED_GLASS_PANE, background.type)
		assertEquals(7.toShort(), background.durability)


		val event = mock(MenuClickEvent::class.java)
		val player = MockBukkit.getMock().addPlayer()
		`when`(event.clicker()).thenReturn(player)

		background.handler().accept(event)

		assertEquals(0.0, player.health)
		assertEquals("You died for clicking on the background!", player.nextMessage())
		assertEquals("You are not OP!", player.nextMessage())
	}
}
