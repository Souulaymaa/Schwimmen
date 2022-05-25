package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import java.util.*
import kotlin.collections.ArrayDeque


/**
 * Service layer class that that holds [SchwimmenGame] objects and provides stack-like
 * access to them (with e.g. [initialiseAllCardStacks], [initialiseAllCards]).
 *
 */
class CardService ( private val sgs: SchwimmenGameService) : AbstractRefreshingService() {
    /**
     * The actual backing data structure. As there is no dedicated stack implementation
     * in Kotlin, a "double-ended queue" (Deque) is used.
     * Every Stack is initialized without any cards.
     */
    var cards: ArrayDeque<Card> = ArrayDeque(32)

    /**
     * the amount of cards in this stack
     */
    val size: Int get () = cards.size

    /**
     * Returns `true` if the stack is empty, `false` otherwise.
     */
    val empty: Boolean get() = cards.isEmpty()

    /**
     * Shuffles the cards in this stack
     */
    private fun shuffleCards(){
        cards.shuffle(Random(123))
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
        shuffleCards()
        return cards
    }

    /**
     * Draws Three cards from this card stack.
     *
     * @throws IllegalArgumentException if less than three cards on the card stack.
     */
    fun initializeAllCardStacks(){

        val game = sgs.currentGame
        checkNotNull(game)
        val players = game.players
        val tableStack = game.cardStack.tableStack
        val drawStack = game.cardStack.drawStack

        //initialise each player's cards with 3 cards
        for (player in players){
            repeat(3){
                player.playerCards.add(cards.removeFirst())
            }
        }

        //initialise the table cards with 3 cards
        repeat(3) {
            tableStack.add(cards.removeFirst())
        }

        // initialise the draw stack with the rest of the cards
        repeat(cards.size){
            drawStack.add(cards.removeFirst())
        }
    }

    /**
     * discard a specific card from this card stack.
     *
     * @param card the card we want to discard.
     *
     * @throws IllegalArgumentException if the card stack is empty or
     * the card Stack does not contain the card that we want to discard
     */
    fun discard(card: Card){
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