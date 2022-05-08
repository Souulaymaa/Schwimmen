package entity


enum class CardSuit {
    CLUBS,
    SPADES,
    HEARTS,
    DIAMONDS,
    ;


    override fun toString() = when(this) {
        CLUBS -> "♣"
        SPADES -> "♠"
        HEARTS -> "♥"
        DIAMONDS -> "♦"
    }


}