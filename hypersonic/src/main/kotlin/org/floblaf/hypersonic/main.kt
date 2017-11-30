package org.floblaf.hypersonic

import java.util.*

fun main(args : Array<String>) {
    val bombTimer = 8 // TODO const
    val bombPower = 2

    val input = Scanner(System.`in`)
    val width = input.nextInt()
    val height = input.nextInt()
    val myId = input.nextInt()

    val board = Board(width, height)

    var canBomb = 0

    // game loop
    while (true) {
        board.clear()
        for (y in 0 until height) {
            val row = input.next()
            row.mapIndexed{x, c -> {
                if ('0' == c) {
                    board.set(Box(x,y))
                }
            }}
        }
        val entities = input.nextInt()
        for (i in 0 until entities) {
            val entityType = input.nextInt()
            val owner = input.nextInt()
            val x = input.nextInt()
            val y = input.nextInt()
            val param1 = input.nextInt()
            val param2 = input.nextInt()

            if (entityType == 0) {
                if (myId == owner) {
                    board.player.x = x
                    board.player.y = y
                }
            } else {
                //board.set(Bomb(x, y))
            }
        }

        if (canBomb == 0) {
            println("BOMB ${board.player.x} ${board.player.y}")
            canBomb = bombTimer
        }
        else {
            var targetX = 0
            var targetY = 0
            var bestCount = 0
            for (i in board.player.x-bombTimer..board.player.x+bombTimer) {
                for (j in board.player.y-(bombTimer-Math.abs(i))..board.player.y+(bombTimer-Math.abs(i))) {
                    val hit = board.boxHitByBomb(i, j, bombPower)
                    if (hit >= bestCount) {
                        targetX = i
                        targetY = j
                        bestCount = hit
                    }
                }
            }

            if (bestCount == 0) {
                val target = board.getNearestBox()
                if (target is Box) {
                    println("MOVE ${target.x} ${target.y}")
                }
                else {
                    //TODO nawak
                    println("MOVE 0 0")
                }
            }
            else {
                println("MOVE $targetX $targetY")
            }
            canBomb--
        }

        // To debug: System.err.println("Debug messages...");
    }
}
