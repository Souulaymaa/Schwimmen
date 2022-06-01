package view

import entity.Player
import service.SchwimmenGameService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color
import kotlin.system.exitProcess

/**
 * [MenuScene] that is displayed when the game is finished. It shows the winner of the game
 * as well as the score of each player. In addition to that, there are two buttons: one for starting
 * a new game and one for quitting the program.
 */
class EndGameScene (private val sgs: SchwimmenGameService)
    : MenuScene(1280, 1080, background = ColorVisual(Color.CYAN)), Refreshable{

    //players scores labels
    private val p1Score = Label(width = 600, height = 35, posX = 50, posY = 125, font = Font(size = 35))
    private val p2Score = Label(width = 600, height = 35, posX = 50, posY = 160, font = Font(size = 35))
    private val p3Score = Label(width = 600, height = 35, posX = 50, posY = 195, font = Font(size = 35))
    private val p4Score = Label(width = 600, height = 35, posX = 50, posY = 230, font = Font(size = 35))

    //label for the game winner
    private val gameResult : Label = Label(
        width = 600, height = 35,
        posX = 50, posY = 450,
        font = Font(35, Color(54,65,45)),
        alignment = Alignment.CENTER
    )


    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 50,
        text = "Spielende",
        font = Font(size = 45),
        alignment = Alignment.CENTER
    )

    private val quitButton = Button(
        width = 320, height = 80,
        posX = 250, posY = 800,
        text = "Beenden",
        font = Font(34)
    ).apply {
        visual = ColorVisual(221, 136, 136)
        onMouseClicked = { exitProcess(0) }
    }

    val newGameButton = Button(
        width = 320, height = 80,
        posX = 700, posY = 800,
        text = "Neustarten",
        font = Font(34)
    ).apply {
        visual = ColorVisual(136, 221, 136)
    }

    init {
        opacity = .7
        addComponents(headlineLabel, p1Score, p2Score, p3Score, p4Score, quitButton, newGameButton, gameResult)
    }

    /**
     * get the score of each player
     */
    private fun Player.scoreString(): String = "${this.playerName} scored ${this.score} points."

    /**
     * get the name of the winner back or determines a draw if the points are equal.
     */
    private fun gameResultString(): String {
        val game = sgs.currentGame
        checkNotNull(game)
        var res = " "
        val winner = sgs.scoreService.determineWinner()
        for(i in 0..game.players.size-1){
            if(game.players[i].score != winner.score){
                res = "$winner wins the game."
            } else {
                 res = "There is a draw."
            }
        }
        return res
    }

    /**
     * Displays the scores, the winner and the two buttons
     */
    override fun refreshAfterGameEnd() {
        val game = sgs.currentGame
        checkNotNull(game) { "No game running" }
        p1Score.text = game.players[0].scoreString()
        p2Score.text = game.players[1].scoreString()
        if(game.players.size >= 3) p3Score.text = game.players[2].scoreString()
        if(game.players.size == 4) p4Score.text = game.players[3].scoreString()
        gameResult.text = gameResultString()
    }



}