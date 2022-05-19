package entity


import kotlin.random.Random

/**
 * Data structure that holds [SchwimmenGame] objects and provides stack-like
 *
 *
 * @param [tableStack] The table cards
 * @param [drawStack] The stack for the rest of the cards
 */
class CardStack (var tableStack : MutableList<Card> = ArrayList(), var drawStack : MutableList<Card> = ArrayList())
