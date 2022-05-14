package service

import entity.*
import java.util.*


/**
 * Service layer class that provides the logic for actions not directly related to a player.
 */
class SchwimmenGameService(): AbstractRefreshingService() {

    val scoreService : ScoreService = ScoreService(this)
    val playerActionService = PlayerActionService(this)

    /**
     * The currently active game. Can be `null`, if no game has started yet.
     */
    var currentGame : SchwimmenGame? = null


    /**
     * Creates a [SchwimmenGame] with the [Player]s named in the [List].

     * @param playerNames List of the names of the Players to start the Game with.

     */
    fun createNewGame(playerNames: List<String>) {
        // check if the number of players is invalid
        require(playerNames.size <= 4 && playerNames.size >= 2) {
            "Invalid number of players: ${playerNames.size}."
        }
        // get a card stack
        val cardStack = CardStack()
       cardStack.initializeAllCards()

        //calling the help method
        val players = List(playerNames.size) {
            createPlayer(playerNames[it],cardStack)
        }

        //initialize the current game
        currentGame = SchwimmenGame(players , cardStack,  players[0])

        onAllRefreshables {  refreshAfterCreateSchwimmenGame() }

    }

    /**
     * help method
     * Creates a [Player] with the specified name and starting [CardStack].
     *
     * @param playerName Name of the Player.
     * @param cardstack Starting card Stack of the Player.
     */
    private fun createPlayer(playerName: String, cardstack: CardStack): Player {
        return Player(playerName,cardstack)
    }

    /**
     * Method to end the current [SchwimmenGame]. The [Player]s scores are evaluated.
     */

    fun endGame() {

        val game = currentGame
        checkNotNull(game) { "No game started yet." }

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
        //get current
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













