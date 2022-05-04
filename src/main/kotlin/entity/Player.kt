package entity

import java.util.ArrayDeque

class Player( val playerName: String, val playerCards : ArrayDeque<Card> = ArrayDeque(3)) {
    override fun toString(): String = "$playerName"
    val knocked = false
}