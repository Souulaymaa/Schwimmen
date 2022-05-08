package entity

/**
 * Die Klasse für die einzelne Entität die der Spiel kennt.
 *
 * Der Konstruktor erwartet ein Symbol [cardSuit] und einen Wert [cardValue].
 *
 * @param cardSuit Das Suit der Karte.
 * @param cardValue Der Wert der Karte.
 */

class Card(val suit: CardSuit, val value: CardValue) {

    override fun toString() = "$suit$value"

    /**
     *  compares two [WarCard]s according to the [Enum.ordinal] value of their [CardSuit]
     */
    operator fun compareTo(other: Card) = this.value.ordinal - other.value.ordinal
}