package service
import entity.*


class PlayerActionService(private val sgs : SchwimmenGameService) : AbstractRefreshingService() {

    //get current game
    val game = this.sgs.currentGame
    fun exchangeAllCards() {

        //set pass counter to o
        game.resetPassCount()

        //remove and save current first player of queue
        var currentPlayer = game.players.poll()

        //get the table cards
        var tableStack = game.cardStack.tableStack

        //swap player Cards and table Cards
        var cardCache: ArrayList<Card> = ArrayList(3)
        move(currentPlayer.playerCards, cardCache)
        move(tableStack, currentPlayer.playerCards)
        move(cardCache, tableStack)

        //add player to the end of queue
        game.players.add(currentPlayer)

        //check if next Player has knocked
        if (game.players.peek().knocked == true) {
            sgs.endGame()
        } else {
            //onAllRefreshables { refreshCards() }
        }
    }

    fun exchangeOneCard(PlayerCardNum : Int, tableCardNum: Int){
        //set passCount to 0
        game.resetPassCount()

        //remove and save current first player of queue
        var currentPlayer = game.players.poll()

        //get the table cards
        var tableStack = game.cardStack.tableStack

        //swap player card and table card
        var cache : Card = currentPlayer.playerCards[PlayerCardNum]
        currentPlayer.playerCards[PlayerCardNum] = tableStack[tableCardNum]
        tableStack[tableCardNum] = cache

        //add player to the end of queue
        game.players.add(currentPlayer)

        //check if next Player has knocked
        if (game.players.peek().knocked == true) {
            sgs.endGame()
        } else {
            //onAllRefreshables { refreshCards() }
        }
    }

    fun pass() {
        //increment Pass Counter
        game.incrementPassCount()
        var playerCount = sgs.currentGame.players.count()
        var passCounter = game.passCount
        //get the table cards and the draw cards
        var tableStack = game.cardStack.tableStack
        var drawStack = game.cardStack.drawStack

        //check if all players passed in a row
        if(passCounter == playerCount){
            if(drawStack.size >= 3){
                discardCards()
                game.resetPassCount()
            }
            else {
                sgs.endGame()
            }
        }
        else {
            game.incrementPassCount()
        }

        //check if next Player has knocked
        if (game.players.peek().knocked == true) {
            sgs.endGame()
        } else {
            //onAllRefreshables { refreshCards() }
        }

    }

    fun knock(){
        val currentPlayer = game.players.poll()
        currentPlayer.knocked == true
        game.resetPassCount()

        //add player to the end of queue
        game.players.add(currentPlayer)

    }


    fun <T : Any?> move(source: MutableList<T>, target: MutableList<T>){
        for(i in 1..3){
            target.add(source.removeFirst())
        }
    }

    fun discardCards(){
        var cardStack = game.cardStack
        for (index in 1..3){
            cardStack.tableStack.removeFirst()
            cardStack.tableStack.add(cardStack.drawStack.removeFirst())
        }

        }


}