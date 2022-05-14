package service

import entity.*

/**
 * Service layer class that provides the logic for the four possible actions a player
 * can take in Schwimmen: exchange all cards, exchange one card, pass and knock.
 */
class PlayerActionService (private val rootService: RootService) : AbstractRefreshingService() {

    /**
     * Method that implements the [Player] action knock.
     *
     * The pass counter passCount is set to 0 and the current Player is marked as a knocker.
     *
     * @throws IllegalStateException If there is no currently active game.
     */
    fun knock() {
        //get current game
        val game = rootService.currentGame
        checkNotNull(game)

        //mark the current player as a knocker
        game.currentPlayer.knocked = true
        rootService.gameService.endMove()
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
        val game = rootService.currentGame
        checkNotNull(game)
        // increment the pass counter
        game.incrementPassCount()
        if (game.passCount == game.players.size) {
            //the counter is set to 0 since all players have passed
            game.resetPassCount()
            if (game.cardStack.size >= 3) {
                // hilfsfunktion
                replaceTableCards()

                 onAllRefreshables {
                      refreshCards()
                  }
                rootService.gameService.endMove()
            } else {
                // if we have less than 3 cards in the card stack
                rootService.gameService.endGame()
            }
        } else {

            rootService.gameService.endMove()
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
        val game = rootService.currentGame
        checkNotNull(game)
        // initialise the table cards with three cards from the card stacks
        game.tableStack = game.cardStack.drawThree()

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
        val game = rootService.currentGame
        checkNotNull(game)
        //the counter is set to 0
        game.resetPassCount()
        // dreieckstausch
        val temp = game.tableStack[tableCardPos]
        game.tableStack[tableCardPos] = game.currentPlayer.playerCards[playerCardPos]
        game.currentPlayer.playerCards[playerCardPos] = temp

        onAllRefreshables {
            refreshCards()
        }
        rootService.gameService.endMove()

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
        val game = rootService.currentGame
        checkNotNull(game)

        game.resetPassCount()

        val temp = game.tableStack
        game.tableStack = game.currentPlayer.playerCards
        game.currentPlayer.playerCards = temp

        onAllRefreshables {
            refreshCards()
        }
        rootService.gameService.endMove()

    }



}








