package entity
import java.util.*

/**
 * Eine Entitätsklasse, die der Spiel darstellt.
 *
 * @param [players] Die Liste der Spieler
 * @throws IllegalArgumentException wenn die Anzahl der Spieler ungültig ist
 */
class SchwimmenGame(val players : Queue<Player> = LinkedList<Player>(), val cardStack: CardStack = CardStack()){
    var passCount: Int = 0

    //set pass Counter as 0
    fun resetPassCount(){
        passCount = 0
    }

    //increment Pass Counter by 1
    fun incrementPassCount(){
        passCount ++
    }

    init {
        if(players.count() < 2 || 4 < players.count()){
            throw IllegalArgumentException("The number of Players should be 2-4")
        }
    }
}


