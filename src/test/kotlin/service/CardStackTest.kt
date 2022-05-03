package gametest

import kotlin.test.*
import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.CardStack

/**
 * Test cases for [CardStack]
 */
class CardStackTest {
    private val aceOfSpades = Card(CardSuit.SPADES, CardValue.ACE)
    private val jackOfClubs = Card(CardSuit.CLUBS, CardValue.JACK)
    private val queenOfHearts = Card(CardSuit.HEARTS, CardValue.QUEEN)

    @Test
    fun testOrder() {

        val stack = CardStack()

        stack.putOnTop(listOf(aceOfSpades, jackOfClubs, queenOfHearts))

        assertSame(stack.peek(), queenOfHearts)
        assertSame(stack.draw().first(), queenOfHearts)
        assertSame(stack.peek(), jackOfClubs)
        assertEquals(stack.size, 2)

        stack.putOnTop(queenOfHearts)
        assertSame(stack.peek(), queenOfHearts)
        assertEquals(stack.size, 3)

    }

    @Test
    fun testDrawFail() {
        val stack = CardStack()
        assertFails { stack.draw() }
        stack.putOnTop(listOf(aceOfSpades, jackOfClubs, queenOfHearts))
        stack.drawAll()
        assertFails { stack.draw() }
    }
}