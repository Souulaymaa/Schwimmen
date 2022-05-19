package entity

/**
 * * Entity to represent a [Player] in the game "Schwimmen"
 *
 * The constructor is waiting for a name, the player cards and the Variable [knocked],
 * that shows, that the player has knocked.
 *
 * @param [playerName] the player name
 * @param [playerCards] the player cards
 * @param [score] the player score
 * @throws IllegalArgumentException When the player's name is empty
 */

class Player( val playerName: String, var playerCards: MutableList<Card> = ArrayList()) {
    override fun toString(): String = "$playerName"

    var knocked: Boolean = false
    var score: Double = 0.0

    //set the constraints
    init{
        if(playerName == ""){
            throw IllegalArgumentException("The Player name is empty")
        }
    }
}