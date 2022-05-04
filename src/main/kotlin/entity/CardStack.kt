package entity
import java.util.*


class CardStack ( val drawStack: ArrayList<Card> , var tableStack : ArrayList<Card>) {

    init{
        if(tableStack.size != 3){
            throw IllegalArgumentException("The number of table cards does not equal 3")
        }
        if(drawStack.size > 32 || drawStack.size < 1){
            throw IllegalArgumentException("Invalid number of cards on the stack")
        }
    }

}