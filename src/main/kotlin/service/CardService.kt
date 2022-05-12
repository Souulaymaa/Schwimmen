package service

import entity.*
import java.util.*

class CardService(private val sgs : SchwimmenGameService) : AbstractRefreshingService() {

    private val cards : ArrayList<Card> = ArrayList(32)

    init{
        cards.shuffle()
    }

    fun initializeAllCardStacks(){
        val drawstack = sgs.currentGame.cardStack.drawStack
        val tablestack = sgs.currentGame.cardStack.tableStack
        val players = sgs.currentGame.players

        //intialise table cards
        for (i in 0..2){
            tablestack.add(cards.removeFirst())
        }

        //initialise players' cards
        for(j in players){
            for(num in 0..2){
            j.playerCards.add(cards.removeFirst())
            }
        }

        //initialise the draw stack with the cards that are left
        for(n in 0..cards.size){
            drawstack.add(cards.removeFirst())
        }
    }



    fun initializeCards(){
        for(suit in CardSuit.values()){
            for(value in CardValue.values()){
                cards.add(Card(suit, value))
            }
        }
    }
}