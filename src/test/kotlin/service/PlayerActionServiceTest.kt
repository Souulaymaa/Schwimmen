package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.*

/**
 * Test cases for the [PlayerActionService]
 */
class PlayerActionServiceTest {

    private val rootService = RootService()
    private val playerActionService = PlayerActionService(rootService)
    private val schwimmenGameService = SchwimmenGameService(rootService)


    val card1 = Card(CardSuit.HEARTS, CardValue.QUEEN)
    val card2 = Card(CardSuit.CLUBS, CardValue.SEVEN)
    val card3 = Card(CardSuit.SPADES, CardValue.JACK)


    //initialise players
    val players: List<String> = listOf( "Jack","Adam", "Katherin")

    /**
     * test if action exchangeAllCards works
     */
    @Test
    fun testexchangeAllCards() {
        assertFailsWith<IllegalStateException> { playerActionService.exchangeAllCards() }
        schwimmenGameService.createNewGame(players)
        val currentgame = rootService.currentGame
        requireNotNull(currentgame)
        currentgame.incrementPassCount()
        val currentTableCardsList = currentgame.tableStack
        val currentPlayersCardsList = currentgame.currentPlayer.playerCards

        playerActionService.exchangeAllCards()

        assertEquals(currentgame.currentPlayer,currentgame.players[1])
        assertEquals(currentgame.passCount,0)

        assertEquals(currentgame.players[0].playerCards, currentTableCardsList)
        assertEquals(currentgame.tableStack,currentPlayersCardsList)

    }

    /**
     * test if action exchangeOneCard works
     */
    @Test
    fun testswapOne(){
        //if there is no currently active game
        assertFailsWith<IllegalStateException> { playerActionService.exchangeOneCard(0,0) }

// if there is a currently active game
        schwimmenGameService.createNewGame(players)

        val currentgame = rootService.currentGame
        requireNotNull(currentgame)

        currentgame.incrementPassCount() // Increment numberPassed to check if it is set back to 0 correctly.

        val currentTableCardsList = currentgame.tableStack
        val currentPlayersCardsList = currentgame.currentPlayer.playerCards

        // same positions
        playerActionService.exchangeOneCard(0,0)

        assertEquals(currentgame.currentPlayer,currentgame.players[1]) // Check if the player was advanced.
        assertEquals(currentgame.passCount,0) // Check if numberPassed was set back to 0.

        // Check if hand of player was altered correctly.
        //assertEquals(currentGame.players[0].hand[0],currentMiddleCardsList[0])
        assertEquals(currentgame.players[0].playerCards[1],currentPlayersCardsList[1])
        assertEquals(currentgame.players[0].playerCards[2],currentPlayersCardsList[2])
        assertEquals(currentgame.tableStack.size,3)
        assertEquals(currentgame.players[0].playerCards.size,3)

        // Check if middle was altered correctly.
        // assertEquals(currentGame.middle[0],currentPlayersCardsList[0])
        assertEquals(currentgame.tableStack[1],currentTableCardsList[1])
        assertEquals(currentgame.tableStack[2],currentTableCardsList[2])
        assertEquals(currentgame.tableStack.size,3)
        assertEquals(currentgame.players[0].playerCards.size,3)

        // different positions
        val currentTableCardsList1 = currentgame.tableStack
        val currentPlayersCardsList1 = currentgame.currentPlayer.playerCards

        playerActionService.exchangeOneCard(2,1)

        assertEquals(currentgame.currentPlayer,currentgame.players[2]) // Check if the player was advanced.

        // Check if hand of player was altered correctly.

        assertEquals(currentgame.players[1].playerCards[0],currentPlayersCardsList1[0])
        assertEquals(currentgame.players[1].playerCards[1],currentPlayersCardsList1[1])
        //  assertEquals(currentGame.players[1].hand[2],currentMiddleCardsList1[1])
        assertEquals(currentgame.tableStack.size,3)
        assertEquals(currentgame.players[1].playerCards.size,3)

        // Check if middle was altered correctly.
        assertEquals(currentgame.tableStack[0],currentTableCardsList1[0])
        //    assertEquals(currentGame.middle[1],currentPlayersCardsList1[2])
        assertEquals(currentgame.tableStack[2],currentTableCardsList1[2])
        assertEquals(currentgame.tableStack.size,3)
        assertEquals(currentgame.players[1].playerCards.size,3)
    }

    /**
     * test if action pass works
     */
    @Test
    fun testpass(){
        assertFailsWith<IllegalStateException> { playerActionService.pass() }//if there is no currently active game.

        schwimmenGameService.createNewGame(players)

        val currentgame = rootService.currentGame
        requireNotNull(currentgame)

        val currentTableCardsList = currentgame.tableStack

        assertEquals(currentgame.passCount,0)

        playerActionService.pass()

        assertEquals(currentgame.passCount,1) // Check if numberPassed was incremented.
        assertEquals(currentTableCardsList,currentgame.tableStack) // Check if middle was not changed.
        assertEquals(currentgame.currentPlayer.score,0.0) // Check if endGame was not triggered.
    }

    /**
     * test if action knock works
     */
    @Test
    fun testKnock(){
        assertFailsWith<IllegalStateException> { playerActionService.knock() }//if no currently active game exists

        schwimmenGameService.createNewGame(players)

        val currentgame = rootService.currentGame
        requireNotNull(currentgame)

        assertFalse(currentgame.currentPlayer.knocked)

        playerActionService.knock()

        assertTrue(currentgame.players[0].knocked) // Check if player is marked as having knocked.
        assertSame(currentgame.currentPlayer,currentgame.players[1]) // Check if current player is advanced.
    }

}
