package service

import entity.Player
import entity.Card
import entity.CardSuit
import entity.CardValue


class ScoreService(private val rootService: RootService) : AbstractRefreshingService() {


    /**
     * Calculates the score for every [Player] in the [SchwimmenGame] .
     */
     fun calculateScore() {
        //get the current game
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet." }
        //call the help function evaluateHand for each player
        game.players.forEach {
            it.score = calculateHand(it.playerCards)
        }
    }


    /**
     * Calculates the score for a given [playerCards].
     */
    fun calculateHand(playerCards: MutableList<Card>): Double {

        val score =
            if (playerCards[0].value== playerCards[1].value && playerCards[1].value== playerCards[2].value) {
                30.5
            } else {
                var clubsCounter = 0.0
                var spadesCounter = 0.0
                var heartsCounter = 0.0
                var diamondsCounter = 0.0
                for (i in 0..2){
                    if(playerCards[i].suit==CardSuit.CLUBS){

                        clubsCounter+=counter(playerCards[i])

                    }

                    else if(playerCards[i].suit==CardSuit.SPADES){

                        spadesCounter+=counter(playerCards[i])

                    }

                    else if(playerCards[i].suit==CardSuit.HEARTS){

                        heartsCounter+=counter(playerCards[i])

                    }
                    else if(playerCards[i].suit==CardSuit.DIAMONDS){

                        diamondsCounter+=counter(playerCards[i])

                    }

                }
                maxOf(clubsCounter,spadesCounter,heartsCounter,diamondsCounter)
            }
        return score

    }

    /**
     * help function that return a double represting a card value
     */
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