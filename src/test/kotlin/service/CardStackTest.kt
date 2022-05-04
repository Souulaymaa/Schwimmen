package gametest

import kotlin.test.*
import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.CardStack
import java.lang.IllegalArgumentException

/**
 * Test cases for [CardStack]
 */
class CardStackTest {
    //initialise some table cards and drawstacks to test with
    private var tablecards1 = arrayListOf<Card>(
        Card(CardSuit.HEARTS, CardValue.QUEEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.SPADES, CardValue.JACK))
    private var tablecards2 = arrayListOf<Card>(
        Card(CardSuit.HEARTS, CardValue.QUEEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN))
    private val drawcards1 = arrayListOf<Card>(
        Card(CardSuit.HEARTS, CardValue.QUEEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.SPADES, CardValue.JACK),
        Card(CardSuit.DIAMONDS, CardValue.KING))
    private val drawcards2 = arrayListOf<Card>()


    //test with a wrong number of table cards
    @Test
    fun wrongTableCardsNumber(){
        assertFailsWith<IllegalArgumentException>(){
            CardStack(drawcards1, tablecards2)
        }
    }
    //test with a wrong number of draw cards
    @Test
    fun wrongDrawCardsNumber(){
        assertFailsWith<IllegalArgumentException>(){
            CardStack(drawcards2, tablecards1)
        }
    }
}