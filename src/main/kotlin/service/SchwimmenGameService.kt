package service

import entity.*
import java.util.*


/**
 * Service layer class that provides the logic for actions not directly related to a player.
 */
class SchwimmenGameService: AbstractRefreshingService() {

    private val scoreService : ScoreService = ScoreService(this)
    private val cardService = CardService(this)

    /**
     * The currently active game. Can be `null`, if no game has started yet.
     */
    var currentGame : SchwimmenGame? = null


    /**
     * Creates a [SchwimmenGame] with the [Player]s named in the [List].

     * @param playerNames List of the names of the Players to start the Game with.

     */
    fun createNewGame(playerNames: List<String>) {

        val players = List(playerNames.size) {
            createPlayer(playerNames[it])
        }

        currentGame = SchwimmenGame(players)
        cardService.initializeAllCards()
        cardService.initializeAllCardStacks()
    }

    /**
     * help method
     * Creates a [Player] with the specified name.
     *
     * @param playerName Name of the Player.
     */
    private fun createPlayer(playerName: String): Player {
        return Player(playerName)
    }


    /**
     * Method to end the current [SchwimmenGame]. The [Player]s scores are evaluated.
     */

    fun endGame() {
        scoreService.calculateScore()
        onAllRefreshables { refreshAfterGameEnd() }
    }

    /**
     * Ends the current player action.
     *
     * The current [Player] of [SchwimmenGame] is advanced.
     *
     * If the new current player has knocked, the game will end after the last player in the row has played his turn.
     * Otherwise, the next player's turn will begin.
     */
    fun endMove() {
        //get current game
        val game = currentGame
        checkNotNull(game) { "No game currently running."}

        val x = game.currentPlayer
        //Advances the pointer currentPlayer in [SchwimmenGame] to the next [Player].
        game.currentPlayer = game.players[(game.players.indexOf(game.currentPlayer) + 1) % game.players.size]

        if (game.currentPlayer.knocked && game.players.size==game.players.indexOf(x)+1 ) {

            endGame()

            onAllRefreshables {
                refreshAfterGameEnd()
            }
        }
    }
}

