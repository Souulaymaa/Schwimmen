package service

import entity.*
import java.util.*

/**
 * Main class of the service layer for the War card game. Provides access
 * to all other service classes and holds the [currentGame] state for these
 * services to access.
 */
class SchwimmenGameService : AbstractRefreshingService() {
    var currentGame : SchwimmenGame = SchwimmenGame()

    val cardService : CardService = CardService(this)
    val scoreService : ScoreService = ScoreService(this)
    val playerActionService : PlayerActionService = PlayerActionService(this)


    fun createNewGame(players : Queue<Player>){
        currentGame = SchwimmenGame(players)
        onAllRefreshables { refreshAfterCreateSchwimmenGame()}
    }

    fun endGame() {
        val game = currentGame
        checkNotNull(game) { "No game started yet." }
        onAllRefreshables { refreshAfterGameEnd() }
    }


}