package view

import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.GameComponentView

/**
 * Help class that inherits from [LinearLayout] and has the information about the marked card.
 *
 * This class is especially needed to help visualise the [exchangeOneCard] Action.
 */
class MarkedLinearLayout <T: GameComponentView>(
    posX: Number = 0, posY: Number = 0, width: Number = 0, height: Number = 0)
    : LinearLayout<T>(posX, posY, width, height) {

    private var markedComponent: T? = null

    /**
     * The position of the marked component.
     */
    val markedComponentPosition
        get() = this.components.indexOf(markedComponent)

    /**
     * Method changes the marked [T] according to the passed value and updates the visualization.
     *
     * @param toChange The component we need to change.
     * @exception IllegalArgumentException If the passed component is not null and is not contained exactly once.
     */
    fun changeMarked(toChange: T?){
        markedComponent =
            if(toChange == null){
                null
            }else if(this.components.count {it == toChange} != 1){
                throw IllegalArgumentException(
                    "Component to mark is contained " +
                            "${this.components.count {it == markedComponent}} times in MarkedLinearLayout. " +
                            "Should be contained once.")
            }else if(toChange == markedComponent){
                null
            }else{
                toChange
            }
        visualizeMark()
    }

    /**
     * Helps visualising the mark that occured -Changing the Y Position-
     *
     * @exception IllegalStateException If there are fewer than two components.
     */
    private fun visualizeMark(){
        if(this.components.size < 2){
            throw IllegalStateException("There are fewer than two components in the markedLinearLayout.")
        }else{
            val basePosY = this.components.minOf { it.posY }
            this.components.forEach {
                it.posY = basePosY
            }
            val currentlyMarked = markedComponent
            if(currentlyMarked != null){
                currentlyMarked.posY = basePosY + 0.2 * currentlyMarked.height
            }
        }
    }

    /**
     * Clears the components and resets markedComponent to null.
     */
    fun clearIncludingMark(){
        markedComponent = null
        this.clear()
    }
}