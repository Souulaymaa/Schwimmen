package entity

import kotlin.random.Random
import kotlin.test.*

/**
 * Test cases for [CardStack]
 */

class CardStackTest {
    val card1 = Card(CardSuit.HEARTS, CardValue.EIGHT)
    val card2 = Card(CardSuit.HEARTS, CardValue.TEN)
    val card3 = Card(CardSuit.HEARTS, CardValue.QUEEN)

    /**
     * Ensure card stack behavior ( draw ,peek, discard, create and check if empty)
     */
    @Test
    fun testinitializeAllCards() {

        val stack = CardStack()

        assertEquals(stack.initializeAllCards().size, 32)
        stack.putOnTop(mutableListOf(card1, card2, card3))
        assertEquals(stack.size, 35)

    }

    /**
     * Test if Drawthree works
     *  Test if drawing from an empty stack throws an exception
     */
    @Test
    fun testDrawThree() {
        val stack = CardStack()
        assertFails { stack.drawThree() }
        stack.initializeAllCards()
        stack.drawThree()
        assertEquals(stack.size, 29)

    }

    /**
     * Test if discard works
     * Test if discarding from an empty stack throws an exception
     */
    @Test
    fun testDiscard() {
        val stack = CardStack()
        assertFails { stack.discard(card1) }
        stack.putOnTop(mutableListOf(card1, card2))
        assertFails { stack.discard(card3) }
        stack.discard(card2)
        assertEquals(stack.size, 1)
    }

    /**
     * Test if peekThree works
     * Test if peeking from an empty stack throws an exception
     */
    @Test
    fun testPeekThree() {
        val stack = CardStack()
        assertFails { stack.peekThree() }
        stack.initializeAllCards()

        stack.peekThree()
        assertEquals(stack.size, 32)


    }

    /**
     * Test for isEmpty
     */
    @Test
    fun testIsEmpty() {
        val stack = CardStack()
        assertTrue(stack.isEmpty())
        stack.initializeAllCards()
        assertTrue(!stack.isEmpty())


    }

}


