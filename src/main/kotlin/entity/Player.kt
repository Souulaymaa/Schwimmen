package entity

import java.util.ArrayDeque

/**
 * * Entity to represent a [Player] in the game "Schwimmen"
 *
 * The constructor is waiting for a name, the player cards and the Variable [knocked],
 * that shows, that the player has knocked.
 *
 * @param [playerName] the player name
 * @param [playerCards] the player cards
 * @param [cardStack] Die cards stack
 * @param [score] the player score
 * @throws IllegalArgumentException When the player's name is empty
 * @throws IllegalArgumentException When the size of the player cards is invalid
 */

class Player( val playerName: String, val cardStack: CardStack = CardStack()) {
    override fun toString(): String = "$playerName"

    var knocked: Boolean = false
    var score: Double = 0.0
    var playerCards = cardStack.drawThree()

    //set the constraints
    init{
        if(playerName == ""){
            throw IllegalArgumentException("The Player name is empty")
        }
        if(playerCards.size != 3){
            throw IllegalArgumentException("The number of cards is not valid")
        }
    }
}