package org.floblaf.hypersonic.entity

import java.util.*

class Game(width: Int, height: Int, playerId: Int) {

    private var canBomb = 0
    private val bombTimer = 8 // TODO const
    private val bombPower = 2


    val player = User(0, 0, playerId)
    val board = Board(width, height)

    fun refreshWithInput(scanner: Scanner) {
        board.clear()
        for (y in 0 until board.height) {
            scanner.next().forEachIndexed { x, c ->
                when (c) {
                    '0' -> board.set(Box(x, y))
                }
            }
        }
        val entities = scanner.nextInt()
        for (i in 0 until entities) {
            val entityType = scanner.nextInt()
            val owner = scanner.nextInt()
            val x = scanner.nextInt()
            val y = scanner.nextInt()
            val param1 = scanner.nextInt()
            val param2 = scanner.nextInt()

            when {
                entityType == 0 && player.id == owner -> {
                    player.x = x
                    player.y = y
                }
                entityType == 1 -> board.set(Bomb(x, y))
            }
        }
    }

    fun play() {
        if (canBomb == 0) {
            println("BOMB ${player.x} ${player.y}")
            canBomb = bombTimer
        }
        else {
            var targetX = 0
            var targetY = 0
            var bestCount = 0
            for (i in player.x-bombTimer..player.x+bombTimer) {
                for (j in player.y-(bombTimer-Math.abs(i))..player.y+(bombTimer-Math.abs(i))) {
                    val hit = board.boxHitByBomb(i, j, bombPower)
                    if (hit >= bestCount) {
                        targetX = i
                        targetY = j
                        bestCount = hit
                    }
                }
            }

            if (bestCount == 0) {
                val target = board.getNearestBox(player.x, player.y)
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
    }
}