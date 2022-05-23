package gametest

import kotlin.test.*
import entity.*
import java.lang.IllegalArgumentException

/**
 * test cases von [Player]
 */
class Playertest {

    val handcards = arrayListOf<Card>(
        Card(CardSuit.HEARTS, CardValue.QUEEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.SPADES, CardValue.JACK))

    /**
     * test with an empty player's name
     */

    @Test
    fun testEmpty(){
        assertFailsWith<IllegalArgumentException>(){Player("", handcards)}
    }

    /**
     * test with an valid player's name
     */
    @Test
    fun testName(){
        val player = Player("Anna", handcards)
        assertEquals(player.playerName, "Anna")
    }


}