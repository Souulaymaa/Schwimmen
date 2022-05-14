package gametest

import kotlin.test.*
import entity.Card
import entity.CardSuit
import entity.CardValue

/**
 * Test cases for [Card]
 */
class CardTest {
    //initialise some cards to test with
    private val queenOfHearts = Card(CardSuit.HEARTS, CardValue.QUEEN)
    private val sevenOfClubs = Card(CardSuit.CLUBS, CardValue.SEVEN)
    private val jackOfSpades = Card(CardSuit.SPADES, CardValue.JACK)
    private val anotherJackOfSpades = Card(CardSuit.SPADES, CardValue.JACK)
    private val kingOfDiamonds = Card(CardSuit.DIAMONDS, CardValue.KING)

    // unicode characters for the suits
    private val heartsChar = '\u2665' // ♥
    private val diamondsChar = '\u2666' // ♦
    private val spadesChar = '\u2660' // ♠
    private val clubsChar = '\u2663' // ♣

    /**
     * we check if method toString works for some test cards
     */
    @Test
    fun testToString(){
        assertEquals(heartsChar + "Q", queenOfHearts.toString())
        assertEquals(clubsChar + "7", sevenOfClubs.toString())
        assertEquals(spadesChar + "J", jackOfSpades.toString())
        assertEquals(diamondsChar + "K", kingOfDiamonds.toString())
    }

    /**
     * Check if toString produces a 2 character string for every possible card
     * except the 10 (for which length=3 is ensured)
     */
    @Test
    fun testToStringLength(){
        CardSuit.values().forEach { suit ->
            CardValue.values().forEach { value ->
                if (value == CardValue.TEN)
                    assertEquals(3, Card(suit, value).toString().length)
                else
                    assertEquals(2, Card(suit, value).toString().length)
            }
        }
    }

    /**
     * Check if the order introduced by [Card.compareTo] allows
     * to directly compare the value of two cards like `card1 > card2`.
     */
    @Test
    fun testCompareTo() {
        assertTrue( sevenOfClubs < queenOfHearts)
        assertFalse(kingOfDiamonds < jackOfSpades)
        assertTrue(jackOfSpades <= kingOfDiamonds)
    }

    /**
     * Check if two cards with the same CardSuit/CardValue combination are not equal
     *  in the sense of the `===` operator.
     */
    @Test
    fun testNotSame() {
        assertNotSame(jackOfSpades, anotherJackOfSpades)
    }

}