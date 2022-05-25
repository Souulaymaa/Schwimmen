package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Test cases for the [ScoreService]
 */
class ScoreServiceTest {

    private val schwimmenGameService = SchwimmenGameService()
    private val scoreService = ScoreService(schwimmenGameService)

    ///initialize some cards
    val card1 = Card(CardSuit.HEARTS, CardValue.QUEEN)
    val card2 = Card(CardSuit.CLUBS, CardValue.SEVEN)
    val card3 = Card(CardSuit.SPADES, CardValue.JACK)

    val card4 = Card(CardSuit.HEARTS, CardValue.TEN)
    val card5 = Card(CardSuit.HEARTS, CardValue.ACE)
    val card6 = Card(CardSuit.HEARTS, CardValue.QUEEN)

    val card7 = Card(CardSuit.HEARTS, CardValue.TEN)
    val card8 = Card(CardSuit.SPADES, CardValue.TEN)
    val card9 = Card(CardSuit.DIAMONDS, CardValue.TEN)

    //initialize a list of players
    val players1: List<String> = listOf( "Jack","Adam", "Katherin")

    /**
     * testing if the method calculateScore gives the accurate score
     */
    @Test
    fun testCalculateScore(){
        schwimmenGameService.createNewGame(players1)

        val currentGame = schwimmenGameService.currentGame
        requireNotNull(currentGame)

        //Fill the players's hands with customized cards
        currentGame.players[0].playerCards.clear()
        listOf(card1,card2,card3).forEach{
            currentGame.players[0].playerCards.add(0,it)
        }

        currentGame.players[1].playerCards.clear()
        listOf(card4,card5,card6).forEach{
            currentGame.players[1].playerCards.add(0,it)
        }

        currentGame.players[2].playerCards.clear()
        listOf(card7,card8,card9).forEach{
            currentGame.players[2].playerCards.add(0,it)
        }
        // calculate the scores
        scoreService.calculateScore()

        assertEquals(currentGame.players[0].score,10.0)
        assertEquals(currentGame.players[1].score,31.0)
        assertEquals(currentGame.players[2].score,30.5)
    }

    /**
     * Test cases for [determineWinner]
     */
    @Test
    fun testDetermineWinner(){
        schwimmenGameService.createNewGame(players1)

        val currentGame = schwimmenGameService.currentGame
        requireNotNull(currentGame)

        //Fill the players's hands with customized cards
        currentGame.players[0].playerCards.clear()
        listOf(card1,card2,card3).forEach{
            currentGame.players[0].playerCards.add(0,it)
        }

        currentGame.players[1].playerCards.clear()
        listOf(card4,card5,card6).forEach{
            currentGame.players[1].playerCards.add(0,it)
        }

        currentGame.players[2].playerCards.clear()
        listOf(card7,card8,card9).forEach{
            currentGame.players[2].playerCards.add(0,it)
        }
        // determines the winner
        val winner = scoreService.determineWinner()
        assertEquals(currentGame.players[1],winner)
    }

}