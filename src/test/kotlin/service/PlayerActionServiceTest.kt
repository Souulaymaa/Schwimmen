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

    private val schwimmenGameService = SchwimmenGameService()
    private val playerActionService = PlayerActionService(schwimmenGameService)
    private val cardService = CardService(schwimmenGameService)

    //initialise table cards
    val tableCards = arrayListOf<Card>(
        Card(CardSuit.HEARTS, CardValue.QUEEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.SPADES, CardValue.JACK))

    val drawCards = arrayListOf(
        Card(CardSuit.DIAMONDS, CardValue.JACK),
        Card(CardSuit.HEARTS, CardValue.EIGHT),
        Card(CardSuit.SPADES, CardValue.NINE),
        Card(CardSuit.SPADES, CardValue.KING),
        Card(CardSuit.DIAMONDS, CardValue.KING),
    )

    //initialise players
    val players: List<String> = listOf( "Jack","Adam", "Katherin")

    /**
     * test if action exchangeAllCards works
     */
    @Test
    fun testexchangeAllCards() {
        assertFailsWith<IllegalStateException> { playerActionService.exchangeAllCards() }
        schwimmenGameService.createNewGame(players)
        val currentgame = schwimmenGameService.currentGame
        requireNotNull(currentgame)
        currentgame.incrementPassCount()

        currentgame.cardStack.tableStack.clear()
        currentgame.cardStack.tableStack.addAll(tableCards)
        val currentTableCardsList =  currentgame.cardStack.tableStack
        val currentPlayersCardsList = currentgame.currentPlayer.playerCards

        playerActionService.exchangeAllCards()

        assertEquals(currentgame.currentPlayer,currentgame.players[1])
        assertEquals(currentgame.passCount,0)

        assertEquals(currentgame.players[0].playerCards, currentTableCardsList)
        assertEquals(currentgame.cardStack.tableStack,currentPlayersCardsList)

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

        val currentgame = schwimmenGameService.currentGame
        requireNotNull(currentgame)

        currentgame.incrementPassCount() // Increment numberPassed to check if it is set back to 0 correctly.

        currentgame.cardStack.tableStack.clear()
        currentgame.cardStack.tableStack.addAll(tableCards)
        val currentTableCardsList =  currentgame.cardStack.tableStack
        val currentPlayersCardsList = currentgame.currentPlayer.playerCards

        // same positions
        playerActionService.exchangeOneCard(0,0)

        assertEquals(currentgame.currentPlayer,currentgame.players[1]) // Check if the player was advanced.
        assertEquals(currentgame.passCount,0) // Check if numberPassed was set back to 0.

        // Check if hand of player was altered correctly.
        //assertEquals(currentGame.players[0].hand[0],currentMiddleCardsList[0])
        assertEquals(currentgame.players[0].playerCards[1],currentPlayersCardsList[1])
        assertEquals(currentgame.players[0].playerCards[2],currentPlayersCardsList[2])
        assertEquals(currentgame.cardStack.tableStack.size,3)
        assertEquals(currentgame.players[0].playerCards.size,3)

        // Check if middle was altered correctly.
        // assertEquals(currentGame.middle[0],currentPlayersCardsList[0])
        assertEquals(currentgame.cardStack.tableStack[1],currentTableCardsList[1])
        assertEquals(currentgame.cardStack.tableStack[2],currentTableCardsList[2])
        assertEquals(currentgame.cardStack.tableStack.size,3)
        assertEquals(currentgame.players[0].playerCards.size,3)

        // different positions
        val currentTableCardsList1 = currentgame.cardStack.tableStack
        val currentPlayersCardsList1 = currentgame.currentPlayer.playerCards

        playerActionService.exchangeOneCard(2,1)

        assertEquals(currentgame.currentPlayer,currentgame.players[2]) // Check if the player was advanced.

        // Check if hand of player was altered correctly.

        assertEquals(currentgame.players[1].playerCards[0],currentPlayersCardsList1[0])
        assertEquals(currentgame.players[1].playerCards[1],currentPlayersCardsList1[1])
        //  assertEquals(currentGame.players[1].hand[2],currentMiddleCardsList1[1])
        assertEquals(currentgame.cardStack.tableStack.size,3)
        assertEquals(currentgame.players[1].playerCards.size,3)

        // Check if middle was altered correctly.
        assertEquals(currentgame.cardStack.tableStack[0],currentTableCardsList1[0])
        //    assertEquals(currentGame.middle[1],currentPlayersCardsList1[2])
        assertEquals(currentgame.cardStack.tableStack[2],currentTableCardsList1[2])
        assertEquals(currentgame.cardStack.tableStack.size,3)
        assertEquals(currentgame.players[1].playerCards.size,3)
    }

    /**
     * test if action pass works
     */
    @Test
    fun testpass(){
        assertFailsWith<IllegalStateException> { playerActionService.pass() }//if there is no currently active game.

        schwimmenGameService.createNewGame(players)

        val currentgame = schwimmenGameService.currentGame
        requireNotNull(currentgame)

        currentgame.cardStack.tableStack.clear()
        currentgame.cardStack.tableStack.addAll(tableCards)
        val currentTableCardsList =  currentgame.cardStack.tableStack

        assertEquals(currentgame.passCount,0)

        playerActionService.pass()

        assertEquals(currentgame.passCount,1) // Check if numberPassed was incremented.
        assertEquals(currentTableCardsList,currentgame.cardStack.tableStack) // Check if middle was not changed.
        assertEquals(currentgame.currentPlayer.score,0.0) // Check if endGame was not triggered.
    }

    /**
     * test if action knock works
     */
    @Test
    fun testKnock(){
        assertFailsWith<IllegalStateException> { playerActionService.knock() }//if no currently active game exists

        schwimmenGameService.createNewGame(players)

        val currentgame = schwimmenGameService.currentGame
        requireNotNull(currentgame)

        assertFalse(currentgame.currentPlayer.knocked)

        playerActionService.knock()

        assertTrue(currentgame.players[0].knocked) // Check if player is marked as having knocked.
        assertSame(currentgame.currentPlayer,currentgame.players[1]) // Check if current player is advanced.
    }
    /**
     * test if action pass works when all players pass in a row
     */
    @Test
    fun AllPlayersPass(){
        assertFailsWith<IllegalStateException> { playerActionService.pass() }//if there is no currently active game.

        schwimmenGameService.createNewGame(players)

        val currentgame = schwimmenGameService.currentGame
        requireNotNull(currentgame)

        currentgame.cardStack.tableStack.clear()
        currentgame.cardStack.tableStack.addAll(tableCards)
        currentgame.cardStack.drawStack.clear()
        currentgame.cardStack.drawStack.addAll(drawCards)
        val currentTableCardsList =  currentgame.cardStack.tableStack

        playerActionService.pass()
        playerActionService.pass()
        playerActionService.pass()

        assertEquals(currentgame.passCount,0)

        assertEquals(currentgame.passCount,0) // Check if numberPassed was incremented.
        assertEquals(currentgame.cardStack.drawStack.size, 2) // check if the draw stack size was reduced by 3.
        assertEquals(currentTableCardsList[0].toString(), "♦J")
        assertEquals(currentTableCardsList[1].toString(), "♥8")
        assertEquals(currentTableCardsList[2].toString(), "♠9")
    }

}
