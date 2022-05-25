package view

import entity.Card
import service.*
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.components.container.*
import tools.aqua.bgw.components.uicomponents.*
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

/**
 * This is the main Scene for the Schwimmen game, in which all game actions happen.
 * Player 1 "sits" is on the bottom half of the screen, player 2 on the right, Player 3 on the top,
 * Player 4 on the left.
 * Each player has their own cards and the choice between the four actions; to pass, to knock, to exchange
 * all the cards or to exchange one card.
 */
class SchwimmenGameScene(private val sgs: SchwimmenGameService)
    : BoardGameScene(background = ColorVisual(62,119,154)), Refreshable {


    private val currentPlayerCardView = LinearLayout<CardView>(
        posX = 600,
        posY = 900,
        width = 300,
        height = 80,
    )

    private val Player2CardView = LinearLayout<CardView>(
        posX = 1500,
        posY = 500,
        width = 300,
        height = 80,
    ).apply {
        rotation = 90.0
    }

    private val Player3CardView = LinearLayout<CardView>(
        posX = 700,
        posY = 150,
        width = 300,
        height = 80,
    ).apply {
        rotation = 180.0
    }

    private val Player4CardView = LinearLayout<CardView>(
        posX = 0,
        posY = 500,
        width = 300,
        height = 80,
    ).apply {
        rotation = 270.0
    }

    private val currentPlayerLabel = Label(
        posX = 700,
        posY = 750,
        width = 400,
        height = 80,
        font = Font(30, Color(15, 20, 15)),
        alignment = Alignment.CENTER,
        visual = ColorVisual(123,126,123)
    )

    var tableCardViews = LinearLayout<CardView>(
        height = 300, width = 700,
        posX = 560, posY = 350,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(140,70,20)
    )

    private val playerPassButton = Button(
        width = 220, height = 80,
        posX = 170, posY = 850,
        text = "Pass", font = Font(24)
    ).apply {
        onMouseClicked = {
            sgs.playerActionService.pass()
            val game = sgs.currentGame
            checkNotNull(game)
            updateMarkedLinearLayout(markedLinearLayoutTable,game.cardStack.tableStack)
            updateMarkedLinearLayout(markedLinearLayoutPlayer,game.currentPlayer.playerCards)
            currentPlayerLabel.text = game.currentPlayer.playerName
            drawStackLable.text = "${sgs.currentGame?.cardStack?.drawStack?.size}"
        }
    }

    private val playerKnockButton = Button(
        width = 220, height = 80,
        posX = 420, posY = 850,
        text = "Knock", font = Font(24)
    ).apply {
        onMouseClicked = {
            sgs.playerActionService.knock()
            val game = sgs.currentGame
            checkNotNull(game)
            updateMarkedLinearLayout(markedLinearLayoutTable,game.cardStack.tableStack)
            updateMarkedLinearLayout(markedLinearLayoutPlayer,game.currentPlayer.playerCards)
            currentPlayerLabel.text = game.currentPlayer.playerName
            drawStackLable.text = "${sgs.currentGame?.cardStack?.drawStack?.size}"
        }
    }

    private val playerExchangeAllButton = Button(
        width = 220, height = 80,
        posX = 170, posY = 950,
        text = "Swap All", font = Font(24)
    ).apply {
        onMouseClicked = {
            sgs.playerActionService.exchangeAllCards()
            val game = sgs.currentGame
            checkNotNull(game)
            updateMarkedLinearLayout(markedLinearLayoutTable,game.cardStack.tableStack)
            updateMarkedLinearLayout(markedLinearLayoutPlayer,game.currentPlayer.playerCards)
            currentPlayerLabel.text = game.currentPlayer.playerName
            drawStackLable.text = "${sgs.currentGame?.cardStack?.drawStack?.size}"
        }
    }

    val playerExchangeOneCardButton = Button(
        width = 220, height = 80,
        posX = 420, posY = 950,
        text = "Swap One", font = Font(24)
    ).apply{
        onMouseClicked = {
            sgs.playerActionService.exchangeOneCard(
                playerCardPos = markedTableCardPosition,
                tableCardPos = markedPlayerCardPosition
            )
            val game = sgs.currentGame
            checkNotNull(game)
            updateMarkedLinearLayout(markedLinearLayoutTable,game.cardStack.tableStack)
            updateMarkedLinearLayout(markedLinearLayoutPlayer,game.currentPlayer.playerCards)
            currentPlayerLabel.text = game.currentPlayer.playerName
            drawStackLable.text = "${sgs.currentGame?.cardStack?.drawStack?.size}"
        }
    }

    val drawStackCard = LinearLayout<CardView>(
        width = 50, height = 80,
        posX = 1300, posY = 800,
    )

    val drawStackLable = Label(
        width = 50, height = 50,
        posX = 1340, posY = 750,
        font = Font(28, Color(5, 5, 5))
    )

     val markedLinearLayoutTable = MarkedLinearLayout<CardView>(
        700, 400, 500, 100
    )

    val markedLinearLayoutPlayer =  MarkedLinearLayout<CardView>(
        posX = 700, posY = 850, width = 500.0, height = 100.0
    )

    val markedTableCardPosition
        get() = markedLinearLayoutTable.markedComponentPosition
    val markedPlayerCardPosition
        get() = markedLinearLayoutPlayer.markedComponentPosition

    override fun refreshAfterCreateSchwimmenGame() {
        val game = sgs.currentGame
        checkNotNull(game)
        currentPlayerLabel.text = game.players[0].playerName
        drawStackLable.text = "${sgs.currentGame?.cardStack?.drawStack?.size}"
        refreshCards()
    }

    override fun refreshCards() {
        currentPlayerCardView.clear()
        tableCardViews.clear()
        Player2CardView.clear()
        Player3CardView.clear()
        Player4CardView.clear()
        val game = sgs.currentGame
        checkNotNull(game)
        val tableCards = game.cardStack.tableStack
        val playerCards = game.currentPlayer.playerCards
        val cardImageLoader = CardImageLoader()

        drawStackCard.add(CardView( front = ImageVisual(cardImageLoader.backImage)))

        Player2CardView.addAll(CardView(
            front = ImageVisual(cardImageLoader.backImage)),
            CardView(front = ImageVisual(cardImageLoader.backImage)),
            CardView(front = ImageVisual(cardImageLoader.backImage)))

        updateMarkedLinearLayout(markedLinearLayoutTable,tableCards)
        updateMarkedLinearLayout(markedLinearLayoutPlayer,playerCards)

        if (game.players.size >= 3) {
        Player3CardView.addAll(CardView(
            front = ImageVisual(cardImageLoader.backImage)),
            CardView(front = ImageVisual(cardImageLoader.backImage)),
            CardView(front = ImageVisual(cardImageLoader.backImage)))}

        if (game.players.size == 4) {
        Player4CardView.addAll(CardView(
            front = ImageVisual(cardImageLoader.backImage)),
            CardView(front = ImageVisual(cardImageLoader.backImage)),
            CardView(front = ImageVisual(cardImageLoader.backImage)))}

    }

    init {
        addComponents(
            currentPlayerCardView,Player2CardView,
            Player3CardView, Player4CardView,
            playerPassButton, playerKnockButton,
            playerExchangeAllButton, playerExchangeOneCardButton,
            tableCardViews, drawStackCard, drawStackLable,
            markedLinearLayoutTable, markedLinearLayoutPlayer,
            currentPlayerLabel
        )
    }

    /**
     * Updates the [MarkedLinearLayout] with the [Card]s from the [Stack].
     *
     * The Cards to be displayed by the MarkedLinearLayout are renewed using the Cards from the Stack.
     * The Stack has to be of size 3.
     */
    fun updateMarkedLinearLayout(markedLinearLayout: MarkedLinearLayout<CardView>, stack: MutableList<Card>){
        if(stack.size != 3){
            throw IllegalArgumentException("The stack is not of size 3.")
        }else{
            markedLinearLayout.clearIncludingMark()
            stack.forEach {
                val cardImageLoader = CardImageLoader()
                val cardView = CardView(
                    height = 200,
                    width = 130,
                    front = ImageVisual(cardImageLoader.frontImageFor(it.suit, it.value)),
                    back = ImageVisual(cardImageLoader.backImage)
                )
                cardView.showFront()
                markedLinearLayout.add(cardView)
            }
            updateLinearLayoutEvents(markedLinearLayout)
        }
    }

    /**
     * Updates the events when a player marks a card.
     */
    private fun updateLinearLayoutEvents(markedLinearLayout: MarkedLinearLayout<CardView>){
        markedLinearLayout.components.forEach { cardView ->
            cardView.onMouseClicked = {
                markedLinearLayout.changeMarked(cardView)
            }
        }
    }

}