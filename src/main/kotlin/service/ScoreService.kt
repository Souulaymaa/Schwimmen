package service

import entity.Player
import entity.Card
import entity.CardSuit
import entity.CardValue


class ScoreService(private val sgs : SchwimmenGameService) : AbstractRefreshingService() {

    //get current game
    val game = sgs.currentGame
    val players = game.players
    var score : Double = 0.0

    fun calculateScore(){
        for(p in players){
            score = evaluateHand(p.playerCards)
        }
    }

    fun evaluateHand(hand : ArrayList<Card>) : Double{
        val score =
            if (hand[0].value== hand[1].value && hand[1].value== hand[2].value) {
                30.5
            } else {
                var clubsCounter = 0.0
                var spadesCounter = 0.0
                var heartsCounter = 0.0
                var diamondsCounter = 0.0
                for (i in 0..2){
                    if(hand[i].suit== CardSuit.CLUBS){

                        clubsCounter+=counter(hand[i])

                    }

                    else if(hand[i].suit== CardSuit.SPADES){

                        spadesCounter+=counter(hand[i])

                    }

                    else if(hand[i].suit== CardSuit.HEARTS){

                        heartsCounter+=counter(hand[i])

                    }
                    else if(hand[i].suit== CardSuit.DIAMONDS){

                        diamondsCounter+=counter(hand[i])

                    }

                }
                maxOf(clubsCounter,spadesCounter,heartsCounter,diamondsCounter)
            }
        return score
    }

    private fun counter(card: Card):Double{
        var counter = 0.0
        if (card.value== CardValue.SEVEN){counter+=7}
        else if (card.value== CardValue.EIGHT){counter+=8}
        else if (card.value== CardValue.NINE){counter+=9}
        else if (card.value== CardValue.TEN){counter+=10}
        else if (card.value== CardValue.JACK){counter+=10}
        else if (card.value== CardValue.QUEEN){counter+=10}
        else if (card.value== CardValue.KING){counter+=10}
        else if (card.value== CardValue.ACE){counter+=11}
        return counter
    }


}