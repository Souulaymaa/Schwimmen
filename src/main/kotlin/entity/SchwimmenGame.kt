package entity
import java.util.*

/**
 * Eine Entitätsklasse, die der Spiel darstellt.
 *
 * @param [players] Die Liste der Spieler
 * @throws IllegalArgumentException wenn die Anzahl der Spieler ungültig ist
 */
class SchwimmenGame(val players : Queue<Player>  ){
    var passCount: Int = 0
    init {
        if(players.count() < 2 || 4 < players.count()){
            throw IllegalArgumentException("The number of Players should be 2-4")
        }
    }
}

