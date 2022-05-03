package entity

import java.util.ArrayDeque

class Player( val playerName: String, val knocked: Boolean, val playerCards : ArrayDeque<Card> = ArrayDeque(3)) {

}