package entity
import java.util.*

class CardStack (val drawStack: ArrayDeque<Card> = ArrayDeque(32), val tableStack : ArrayDeque<Card> = ArrayDeque(3)) {
    val empty: Boolean get() = drawStack.isEmpty()
    val size: Int get() = drawStack.size
}