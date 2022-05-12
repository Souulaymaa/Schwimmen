package gametest

import kotlin.test.*
import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import java.lang.IllegalArgumentException

/**
 * test cases von [Player]
 */
class Playertest {
    //initialise some player cards to test with
    val handcards = arrayListOf<Card>(
     Card(CardSuit.HEARTS, CardValue.QUEEN),
     Card(CardSuit.CLUBS, CardValue.SEVEN),
     Card(CardSuit.SPADES, CardValue.JACK))

    val handcards2 = arrayListOf<Card>(
        Card(CardSuit.HEARTS, CardValue.QUEEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN))

    //test with an empty player's name
    @Test
    fun testEmpty(){
        assertFailsWith<IllegalArgumentException>(){Player("", handcards)}
    }

    //test with an actual player's name
    @Test
    fun testName(){
        val player = Player("Anna", handcards)
        assertEquals(player.playerName, "Anna")
    }

    //test with wrong number of cards
    @Test
    fun wrongCardNumber(){
        assertFailsWith<IllegalArgumentException>(){Player("Anna", handcards2)}
    }
    //test with right number of cards
    @Test
    fun rightCardNumber(){
        val player = Player("Anna", handcards)
        assertEquals(player.playerCards, handcards)
    }
}