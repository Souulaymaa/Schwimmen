package service

import java.util.*
import entity.*

import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import kotlin.test.*

/**
 * Test cases for the [SchwimmenGameService]
 */
class SchwimmenServiceTest {

    private val schwimmenGameService = SchwimmenGameService()
    private val cardService = CardService(schwimmenGameService)

    //initialise some cards
    val card1 = Card(CardSuit.HEARTS, CardValue.QUEEN)
    val card2 = Card(CardSuit.CLUBS, CardValue.SEVEN)
    val card3 = Card(CardSuit.SPADES, CardValue.JACK)

    val tableCards = arrayListOf<Card>(
        Card(CardSuit.HEARTS, CardValue.SEVEN),
        Card(CardSuit.SPADES, CardValue.ACE),
        Card(CardSuit.DIAMONDS, CardValue.QUEEN))

    //initialise players
    val players1: List<String> = listOf( "Jack","Adam", "Katherin")
    val players2: List<String> = listOf( "Jack")
    val players3: List<String> = listOf( "Jack","Adam", "Katherin", "Anna", "Alia")

    /**
     * Test if startNewGame works.
     */
    @Test
    fun createNewGameCaseOne(){
        assertNull(schwimmenGameService.currentGame)

        //if wrong numbers of players are given.
        assertFails { schwimmenGameService.createNewGame(players2) }
        assertFails { schwimmenGameService.createNewGame(players3) }

        schwimmenGameService.createNewGame(players1)

        assertNotNull(schwimmenGameService.currentGame) // Check if game was created.

        val currentGame = schwimmenGameService.currentGame
        requireNotNull(currentGame)
        currentGame.cardStack.tableStack.clear()
        currentGame.cardStack.tableStack.addAll(tableCards)

        // Check if properties of game are correct.
        assertEquals(currentGame.passCount, 0)
        assertEquals(currentGame.cardStack.drawStack.size,32-currentGame.players.size*3-3)
        assertEquals(currentGame.cardStack.tableStack.size,3)

        // Check if properties of each player are correct.
        currentGame.players.forEach {
            assertFalse(it.knocked)
            assertEquals(it.score,0.0)
            assertEquals(it.playerName,players1[currentGame.players.indexOf(it)])
            assertEquals(it.playerCards.size,3)
        }
    }


    /**
     * Test if endGame works correctly if the stacks are not manipulated.
     */
    @Test
    fun endGameCaseOne(){
        assertFailsWith<IllegalStateException> { schwimmenGameService.endGame() }//if there is no currently active game.

        schwimmenGameService.createNewGame(players1)
        schwimmenGameService.endGame()

        val currentGame = schwimmenGameService.currentGame
        requireNotNull(currentGame)
        currentGame.players.forEach {
            assertNotEquals(it.score,0.0)
        }
    }

    /**
     * Test if endGame works correctly.
     * Here, the card stacks are manipulated to allow the testing of functionalities to calculate the score.
     */
    @Test
    fun endGameCaseTwo(){
        schwimmenGameService.createNewGame(players1)

        val currentGame = schwimmenGameService.currentGame
        requireNotNull(currentGame)
        // Fill the players's hands with customized cards.
        currentGame.players[0].playerCards.clear()
        listOf(card1,card2,card3).forEach{
            currentGame.players[0].playerCards.add(0,it)
        }

        currentGame.players[1].playerCards.clear()
        listOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.HEARTS, CardValue.EIGHT),
            Card(CardSuit.DIAMONDS, CardValue.EIGHT)
        ).forEach {
            currentGame.players[1].playerCards.add(0,it)
        }

        currentGame.players[2].playerCards.clear()
        listOf(
            Card(CardSuit.SPADES, CardValue.SEVEN),
            Card(CardSuit.SPADES, CardValue.EIGHT),
            Card(CardSuit.DIAMONDS, CardValue.NINE)
        ).forEach {
            currentGame.players[2].playerCards.add(0,it)
        }

        // End game and check if correct scores were calculated.
        schwimmenGameService.endGame()
        assertEquals(currentGame.players[0].score,10.0)
        assertEquals(currentGame.players[1].score,30.5)
        assertEquals(currentGame.players[2].score,15.0)
    }


    /**
     * Test if endTurn works correctly if the next player has not knocked.
     */
    @Test
    fun endMoveCaseOne(){
        assertFailsWith<IllegalStateException> { schwimmenGameService.endMove() }//if there is no currently active game.

        schwimmenGameService.createNewGame(players1)

        val currentGame = schwimmenGameService.currentGame
        requireNotNull(currentGame)
        assertEquals(currentGame.currentPlayer,currentGame.players[0])

        schwimmenGameService.endMove()
        assertEquals(currentGame.currentPlayer,currentGame.players[1])

        schwimmenGameService.endMove()
        assertEquals(currentGame.currentPlayer,currentGame.players[2])

        schwimmenGameService.endMove()
        assertEquals(currentGame.currentPlayer,currentGame.players[0])

        schwimmenGameService.endMove()
        assertEquals(currentGame.currentPlayer,currentGame.players[1])
    }

    /**
     * Test if endTurn works correctly if the next player has knocked.
     */
    @Test
    fun endMoveCaseTwo(){
        schwimmenGameService.createNewGame(players1)

        val currentGame = schwimmenGameService.currentGame
        requireNotNull(currentGame)
        // Mark the next player as a knocker
        currentGame.players[1].knocked = true
        // The next player's score should be 0.0 at this point.
        assertEquals(currentGame.players[1].score,0.0)
        // This should trigger the method endGame(), meaning that scores are evaluated.
        schwimmenGameService.endMove()
        // The current player should have advanced.
        assertEquals(currentGame.currentPlayer,currentGame.players[1])

    }

}