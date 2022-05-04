package entity

import java.util.ArrayDeque

/**
 * Die Entität [Player].
 *
 * Der Konstruktor erwartet einen Namen, die Handkarten und die Variable [knocked], die uns kennenzeichnet, dass der Spieler
 * geklopft hat.
 *
 * @param playerName Der Spielername.
 * @param playerCards Die Handkarten.
 * @throws IllegalArgumentException Wenn der Spielername leer ist
 */

class Player( val playerName: String, val playerCards : ArrayList<Card>) {
    override fun toString(): String = "$playerName"
    val knocked: Boolean = false
    init{
        if(playerName == ""){
            throw IllegalArgumentException("The Player name is empty")
        }
        if(playerCards.size != 3){
            throw IllegalArgumentException("The number of cards does not equal 3")
        }
    }
}