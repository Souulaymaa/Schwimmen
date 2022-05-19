package entity
import java.util.*

/**
 * Entity class that represents the Schwimmen Game.
 *
 * @param [players] the list of the players
 * @param [cardStack] the card stack
 * @param [currentPlayer] the player that is in the row
 * @param [passCount] the counter of the action pass
 * @throws IllegalArgumentException wenn die Anzahl der Spieler ungültig ist
 */
data class SchwimmenGame(val players: List<Player> = LinkedList(), val cardStack: CardStack = CardStack())
{
    var passCount: Int = 0
    var currentPlayer: Player = players[0]

    /**
     * set pass Counter as 0
     */
    fun resetPassCount(){
        passCount = 0
    }

    /**
     * increment Pass Counter by 1
     * */
    fun incrementPassCount(){
        passCount ++
    }

    //constraint
    init {
        if(players.count() < 2 || 4 < players.count()){
            throw IllegalArgumentException("The number of Players should be 2-4")
        }
    }
}





