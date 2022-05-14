package gametest

import kotlin.test.*
import entity.*
import java.lang.IllegalArgumentException

/**
 * test cases von [Player]
 */
class Playertest {


    /**
     * test with an empty player's name
     */

    @Test
    fun testEmpty(){
        assertFailsWith<IllegalArgumentException>(){Player("")}
    }


}