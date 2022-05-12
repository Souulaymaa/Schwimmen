package entity
import java.util.*
import kotlin.collections.ArrayList

/**
 * Die Klasse für die Entität CardStack die der Kartenstapel und die Tischkarten verwaltet.
 *
 * Der Konstruktor erwartet 2 Stapeln [drawStack] mit maximalem size von 32 und [tableStack] mit genau 3 Karten.
 *
 * @param drawStack Der Stapel der übrigen Karten.
 * @param tableStack Die Tischkarten.
 */
class CardStack ( val drawStack: MutableList<Card> = ArrayList() , var tableStack : MutableList<Card> = ArrayList()) {

    init{
        if(tableStack.size != 3){
            throw IllegalArgumentException("The number of table cards does not equal 3")
        }
        if(drawStack.size > 32 || drawStack.size < 1){
            throw IllegalArgumentException("Invalid number of cards on the stack")
        }
    }

}