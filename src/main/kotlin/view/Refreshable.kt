package view

import service.AbstractRefreshingService

interface Refreshable {

    fun refreshAfterCreateSchwimmenGame()

    fun refreshAfterGameEnd()

    fun refreshCards()

}