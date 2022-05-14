package entity
import java.util.*

/**
 * Entity class that represents the Schwimmen Game.
 *
 * @param [players] the list of the players
 * @param [cardStack] the card stack
 * @param [currentPlayer] the player that is in the row
 * @param [passCount] the counter of the action pass
 * @param [tableStack] the table cards
 * @throws IllegalArgumentException wenn die Anzahl der Spieler ungültig ist
 */
data class SchwimmenGame(val players : List<Player> = LinkedList<Player>(), val cardStack: CardStack = CardStack(),
                    var currentPlayer: Player)
{
    var passCount: Int = 0
    var tableStack: MutableList<Card> = cardStack.drawThree()

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

    init {
        if(players.count() < 2 || 4 < players.count()){
            throw IllegalArgumentException("The number of Players should be 2-4")
        }
    }
}





