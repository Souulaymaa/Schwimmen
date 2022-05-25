package view

import service.AbstractRefreshingService

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * UI classes only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 *
 */

interface Refreshable {

    /**
     * perform refreshes that are necessary after creating a new game
     */
    fun refreshAfterCreateSchwimmenGame(){}

    /**
     * perform refreshes that are necessary after the game finishes
     */
    fun refreshAfterGameEnd(){}

    /**
     * perform refreshes related to the cards actions
     */
    fun refreshCards(){}

}