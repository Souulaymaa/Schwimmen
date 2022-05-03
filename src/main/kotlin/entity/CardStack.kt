package entity
import java.util.*

class CardStack (private val drawStack: ArrayDeque<Card> = ArrayDeque(32), val tableStack : ArrayDeque<Card> = ArrayDeque(3)) {
    // check if drawStack still has cards
    val empty: Boolean get() = drawStack.isEmpty()

    // check how many cards there are on the stack
    val size: Int get() = drawStack.size

    //returning the top card from the stack
    fun peek() : Card = drawStack.first()

    fun putOnTop(cards: List<Card>) {
        cards.forEach(this.drawStack::addFirst)
    }

    fun putOnTop(card: Card) {
        drawStack.addFirst(card)
    }

    /**
     * Draws [amount] cards from the draw stack.
     *
     * @param amount the number of cards to draw; defaults to 1 if omitted.
     *
     * @throws IllegalArgumentException if not enough cards on stack to draw the desired amount.
     */
    fun draw(amount: Int = 1): List<Card> {
        require (amount in 1..drawStack.size) { "can't draw $amount cards from $drawStack" }
        return List(amount) { drawStack.removeFirst() }
    }

    fun drawAll(): List<Card> = draw(size)

    override fun toString(): String = drawStack.toString()

}