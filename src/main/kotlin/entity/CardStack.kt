package entity


import kotlin.random.Random

/**
 * Data structure that holds [SchwimmenGame] objects and provides stack-like
 * access to them (with e.g. [drawThree], [initialiseAllCards]).
 *
 * @param [random] can be provided to ensure deterministic behavior of [shuffle]
 */
class CardStack (private val random: Random = Random) {

    /**
     * The actual backing data structure. As there is no dedicated stack implementation
     * in Kotlin, a "double-ended queue" (Deque) is used.
     * Every Stack is initialized without any cards.
     */
    private var cards: ArrayDeque<Card> = ArrayDeque(32)

    /**
     * the amount of cards in this stack
     */
    val size: Int get() = cards.size

    /**
     * Returns `true` if the stack is empty, `false` otherwise.
     */
    val empty: Boolean get() = cards.isEmpty()
    /**
     * Shuffles the cards in this stack
     */
    private fun shuffle(){
        cards.shuffled(random)

    }

    /**
     * The variable cards is initialized with a shuffled Deck using initializeAllCards, that uses shuffle()
     */
    fun initializeAllCards() : MutableList<Card>{
        for(suit in CardSuit.values()){
            for(value in CardValue.values()){
                cards.add(Card(suit, value))
            }
        }
        shuffle()
        return cards
    }

    /**
     * Draws Three cards from this card stack.
     *
     * @throws IllegalArgumentException if less than three cards on the card stack.
     */
    fun drawThree(): MutableList<Card> {
        require (size >= 3) { "Not enough cards in $cards to draw three cards!" }
        return MutableList(3) { cards.removeFirst() }
    }

    /**
     * discard a specific card from this card stack.
     *
     * @param card the card we want to discard.
     *
     * @throws IllegalArgumentException if the card stack is empty or
     * the card Stack does not contain the card that we want to discard
     */
    fun discard(card:Card){
        require (!empty && cards.contains(card)){"No card $card in $cards!"}
        cards.remove(card)

    }

    /**
     * returns the three top cards from the card stack without removing them from the stack.
     * @throws IllegalArgumentException if the card stack is empty
     */
    fun peekThree(): MutableList<Card> {
        require (!empty){"there is No cards!"}
        return listOf(cards[0], cards[1], cards[2]).toMutableList()
    }

    /**
     * Returns `true` if the card stack is empty, `false` otherwise.
     */
    fun isEmpty(): Boolean { return cards.isEmpty()}


    /**
     * Help function for testing
     * puts a given list of cards on top of this card stack, so that
     * the last element of the passed parameter [cards] will be on top of
     * the card stack afterwards.
     *
     */
    fun putOnTop(cards: MutableList<Card>) {
        cards.forEach(this.cards::addFirst)
    }
}
