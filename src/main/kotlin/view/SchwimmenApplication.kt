package view

import service.SchwimmenGameService
import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.dialog.Dialog
import tools.aqua.bgw.dialog.DialogType

/**
 * Implementation of the BGW [BoardGameApplication] for the card game "Schwimmen"
 */
class SchwimmenApplication : BoardGameApplication( "Schwimmen"), Refreshable {

    private val sgs = SchwimmenGameService()
    private val newGameScene = NewGameScene(sgs)
    private val gameScene = SchwimmenGameScene(sgs)

    /**
     *  hide the [MenuScene] to show the [SchwimmenGameScene]
     */
    override fun refreshAfterCreateSchwimmenGame() {
        this.hideMenuScene()
    }

    /**
     *  show the [EndGameScene] when the game finishes
     */
    override fun refreshAfterGameEnd() {
        this.showMenuScene(endGameScene)
    }

    private val endGameScene = EndGameScene(sgs).apply{
        newGameButton.onMouseClicked = {
            showMenuScene(newGameScene)
        }
    }


    init {

        this.showGameScene(gameScene)
        this.showMenuScene(newGameScene)

        sgs.addRefreshable(this)
        sgs.addRefreshable(gameScene)
        sgs.addRefreshable(endGameScene)
        sgs.addRefreshable(newGameScene)
    }

}