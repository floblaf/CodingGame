package org.floblaf.hypersonic.entity

class Board(val width: Int, val height: Int) {

    private val grid: Array<Array<Entity?>> = Array(width, { x -> Array<Entity?>(height, { y -> Floor(x, y) }) })

    fun set(e: Entity) {
        grid[e.x][e.y] = e
    }

    fun clear() {
        for (x in 0 until width) {
            for (y in 0 until height) {
                grid[x][y] = Floor(x, y)
            }
        }
    }

    fun getAt(x: Int, y: Int) : Entity? {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null
        }
        System.err.println("$x, $y, ${grid[x][y]}")
        return grid[x][y]
    }

    fun boxHitByBomb(x: Int, y: Int, power: Int) : Int {
        if (getAt(x, y) != null) {
            -1
        }
        val test = (x-power..x+power).count { getAt(it, 0) is Box }
        System.err.println("$test")
        return (x-power..x+power).count { getAt(it, y) is Box } + (y-power..y+power).count { getAt(x, it) is Box }
    }

    fun getNearestBox(x: Int, y: Int) : Box? {
        var dist = 1
        while (dist <= height || dist <= width) {
            for (i in x-dist..x+dist) {
                for (j in y-(dist-Math.abs(i))..y+(dist-Math.abs(i))) {
                    if (getAt(i, j) is Box) {
                        return getAt(i, j) as Box
                    }
                }
            }
            dist ++
        }

        return null
    }

}