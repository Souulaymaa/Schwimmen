package gametest

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import entity.SchwimmenGame
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SchwimmenGameTest {
    //initialise player's cards
    val handcards = arrayListOf<Card>(
        Card(CardSuit.HEARTS, CardValue.QUEEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.SPADES, CardValue.JACK))

    // initialise some players for the test
    val player1 = Player("Player 1", handcards);
    val player2 = Player("Player 2", handcards);
    val player3 = Player("Player 3", handcards);
    val player4 = Player("Player 4", handcards);
    val player5 = Player("Player 4", handcards);
    val nums: Queue<Player> = LinkedList<Player>(listOf(player1, player2, player3, player4, player5))
    val nums2: Queue<Player> = LinkedList<Player>(listOf(player1, player2, player3))

    //test with invalid number of players
    @Test
    fun invalidPlayersNumber(){
        assertFailsWith<IllegalArgumentException>(){SchwimmenGame(nums)}
    }

    //test with valid number of players
    @Test
    fun validPlayersNumber(){
        val game = SchwimmenGame(nums2)
        assertEquals(nums.remove(), player1)
        assertEquals(nums.remove(), player2)
        assertEquals(nums.remove(), player3)

    }
}