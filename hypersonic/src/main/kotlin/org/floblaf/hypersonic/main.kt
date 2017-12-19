package org.floblaf.hypersonic

import org.floblaf.hypersonic.entity.Box
import org.floblaf.hypersonic.entity.Game
import java.util.*

fun main(args : Array<String>) {

    val input = Scanner(System.`in`)
    val width = input.nextInt()
    val height = input.nextInt()
    val myId = input.nextInt()

    val game = Game(width, height, myId)

    // game loop
    while (true) {
        game.refreshWithInput(input)
        game.play()
        // To debug: System.err.println("Debug messages...");
    }
}
