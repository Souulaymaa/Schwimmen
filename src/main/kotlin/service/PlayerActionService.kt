package service

import entity.*

/**
 * Service layer class that provides the logic for the four possible actions a player
 * can take in Schwimmen: exchange all cards, exchange one card, pass and knock.
 */
class PlayerActionService (private val sgs : SchwimmenGameService) : AbstractRefreshingService() {

    /**
     * Method that implements the [Player] action knock.
     *
     * The pass counter passCount is set to 0 and the current Player is marked as a knocker.
     *
     * @throws IllegalStateException If there is no currently active game.
     */
    fun knock() {
        //get current game
        val game = sgs.currentGame
        checkNotNull(game)
        //mark the current player as a knocker
        game.currentPlayer.knocked = true
        sgs.endMove()
        onAllRefreshables { refreshCards() }
    }


    /**
     * Method that implements the [Player] action pass.
     * The pass counter passCount is incremented.
     *
     * If passCount equals the number of Players (all the players passed in the same round),
     * the [tableStack] are replaced and the pass counter is set to 0.
     *
     * we must check if the card stack still has enough cards. if not we end the game
     *
     * @throws IllegalStateException If there is no currently active game.
     */
    fun pass() {
        //get current game
        val game = sgs.currentGame
        checkNotNull(game)

        val tableCards = game.cardStack.drawStack

        // increment the pass counter
        game.incrementPassCount()
        if (game.passCount == game.players.size) {
            //the counter is set to 0 since all players have passed
            game.resetPassCount()
            if (tableCards.size >= 3) {
                // hilfsfunktion
                replaceTableCards()

                sgs.endMove()
            } else {
                // if we have less than 3 cards in the card stack
                sgs.endGame()
            }
        } else {
            sgs.endMove()
        }
        onAllRefreshables {
            refreshCards()
        }
    }

    /**
     * this is a help function that allows us to exchange the [tableStack].
     * The Cards are replaced by new ones out of the card Stack.
     *
     * @throws IllegalStateException If there is no currently active game
     */
    private fun replaceTableCards() {
        //get current game
        val game = sgs.currentGame
        checkNotNull(game)

        // initialise the table cards with three cards from the card stacks
        val cardStack = game.cardStack
            repeat(3) {
                cardStack.tableStack.removeFirst()
                cardStack.tableStack.add(cardStack.drawStack.removeFirst())
            }
        //cardStack.drawStack.size = cardStack.drawStack.size - 3
        onAllRefreshables { refreshCards() }
    }

    /**
     * Method that implements the [Player] action of exchanging one card from[playerCards] with one from the [tableStack].
     *
     * The pass counter passCount is set to 0 and the exchange(Dreieckstausch) is executed.
     *
     * @exception IllegalStateException If there is no currently active game.
     */
    fun exchangeOneCard(playerCardPos: Int, tableCardPos: Int) {
        //get current game
        val game = sgs.currentGame
        checkNotNull(game)

        val tableCards = game.cardStack.tableStack

        game.resetPassCount() //the counter is set to 0

        // dreieckstausch
        val temp = tableCards[tableCardPos]
        tableCards[tableCardPos] = game.currentPlayer.playerCards[playerCardPos]
        game.currentPlayer.playerCards[playerCardPos] = temp

        onAllRefreshables {
            refreshCards()
        }
       sgs.endMove()
    }

    /**
     * Method that implements the [Player] action of exchanging all the [playerCards] with the [tableStack].
     *
     * The pass counter passCount is set to 0 and the exchange(Dreieckstausch) is executed.
     *
     * @exception IllegalStateException If there is no currently active game.
     */
    fun exchangeAllCards() {
        //get current game
        val game = sgs.currentGame
        checkNotNull(game)

        game.resetPassCount() // pass count set to 0

        // Dreieckstausch
        val temp = game.cardStack.tableStack
        game.cardStack.tableStack = game.currentPlayer.playerCards
        game.currentPlayer.playerCards = temp

        onAllRefreshables {
            refreshCards()
        }
        sgs.endMove()
    }
}








