package service

import entity.Card
import entity.CardStack
import entity.CardSuit
import entity.CardValue
import kotlin.test.*

/**
 * Test cases for [CardStack]
 */

class CardServiceTest {
    private val  sgs = SchwimmenGameService()
    val card1 = Card(CardSuit.HEARTS, CardValue.EIGHT)
    val card2 = Card(CardSuit.HEARTS, CardValue.TEN)
    val card3 = Card(CardSuit.HEARTS, CardValue.QUEEN)

    /**
     * Ensure card stack behavior ( draw ,peek, discard, create and check if empty)
     */
    @Test
    fun testinitializeAllCards() {
        val stack = CardService(sgs)
        assertEquals(stack.initializeAllCards().size, 32)
        stack.putOnTop(mutableListOf(card1, card2, card3))
        assertEquals(stack.size, 35)

    }

    /**
     * Test if Drawthree works
     *  Test if drawing from an empty stack throws an exception
     */
    @Test
    fun testitializeAllCardStacks() {
        val stack = CardService(sgs)
        assertEquals(stack.initializeAllCards().size, 32)
        stack.putOnTop(mutableListOf(card1, card2, card3))
        assertEquals(stack.size, 35)

    }

    /**
     * Test if discard works
     * Test if discarding from an empty stack throws an exception
     */
    @Test
    fun testDiscard() {
        val stack = CardService(sgs)
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
        val stack = CardService(sgs)
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
        val stack = CardService(sgs)
        assertTrue(stack.isEmpty())
        stack.initializeAllCards()
        assertTrue(!stack.isEmpty())
    }
}


