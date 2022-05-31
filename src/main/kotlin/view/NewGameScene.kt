package view

import tools.aqua.bgw.core.MenuScene
import service.SchwimmenGameService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * [MenuScene] used to start a new game. It is displayed directly at program start or reached
 * when "new game" is clicked in [EndGameScene]. After providing the names of the wanted players,
 * [startButton] can be pressed.
 */
class NewGameScene (private val sgs: SchwimmenGameService)
    : MenuScene(1280, 1080, background = ColorVisual(Color.CYAN)), Refreshable{

    private val headlineLabel = Label(
        width = 1180, height = 50, posX = 50, posY = 50,
        text = "Schwimmen",
        font = Font(size = 60, Color(5, 5, 5)),
        alignment = Alignment.CENTER
    )


    private val p1Input : TextField = TextField(
        posX = 450, posY = 350, width = 300, height = 50,
        text = "Player 1", font = Font(24)).apply {
        onKeyTyped = {
            startButton.isDisabled = createPlayerNameList().size < 2
        }
    }
    private val p2Input : TextField = TextField(
        posX = 450, posY = 450, width = 300, height = 50,
        text = "Player 2", font = Font(24)).apply {
        onKeyTyped = {
            startButton.isDisabled = createPlayerNameList().size < 2
        }
    }
    private val p3Input : TextField = TextField(
        posX = 450, posY = 550, width = 300, height = 50,
        text = "Player 3", font = Font(24)).apply {
        onKeyTyped = {
            startButton.isDisabled = createPlayerNameList().size < 2
        }
    }
    private val p4Input : TextField = TextField(
        posX = 450, posY = 650, width = 300, height = 50,
        text = "Player 4", font = Font(24)).apply {
        onKeyTyped = {
            startButton.isDisabled = createPlayerNameList().size < 2
        }
    }

    private val addPlayer3Button: Button = Button(
        posX = 780, posY = 550, width = 50, height = 50,
        text = "+", font = Font(24), visual = ColorVisual(229, 154, 123)
    ).apply {
        onMouseClicked = {
            p3Input.isDisabled = !p3Input.isDisabled
        }
    }

    private val addPlayer4Button: Button = Button(
        posX = 780, posY = 650, width = 50, height = 50,
        text = "+", font = Font(24), visual = ColorVisual(229, 154, 123)
    ).apply {
        onMouseClicked = {
            p4Input.isDisabled = !p4Input.isDisabled
        }
    }

    private val startButton = Button(
        width = 300, height = 80,
        posX = 450, posY = 750,
        text = "Start",
        font = Font(34)

    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            val playerList = createPlayerNameList()
            if (playerList.size >= 2) {
                sgs.createNewGame(createPlayerNameList())
            }
        }
    }

    /**
     * create a list with the given players names
     */
    private fun createPlayerNameList(): List<String> {
        val playerNames: MutableList<String> = mutableListOf()
        if (p1Input.text.trim().isNotBlank()) playerNames.add(p1Input.text.trim())
        if (p2Input.text.trim().isNotBlank()) playerNames.add(p2Input.text.trim())
        if (!p3Input.isDisabled && p3Input.text.trim().isNotBlank()) playerNames.add(p3Input.text.trim())
        if (!p4Input.isDisabled && p4Input.text.trim().isNotBlank()) playerNames.add(p4Input.text.trim())
        return playerNames.toList()
    }

    init {
        opacity = .5
        p3Input.isDisabled = true
        p4Input.isDisabled = true
        addComponents(
            headlineLabel,
            p1Input, p2Input,
            p3Input, p4Input,
            addPlayer3Button, addPlayer4Button,
            startButton
        )
    }

}