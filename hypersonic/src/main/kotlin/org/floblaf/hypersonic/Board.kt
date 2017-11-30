package org.floblaf.hypersonic

class Board(val width: Int, val height: Int) {

    val player = User(0,0)
    private val matrice : Array<Array<Entity?>> = Array(width, { Array<Entity?>(height, { null }) })

    fun set(e: LocatedEntity) {
        matrice[e.x][e.y] = e
    }

    fun clear() {
        for (x in 0 until width) {
            for (y in 0 until height) {
                matrice[x][y] = null
            }
        }
    }

    fun getAt(x: Int, y: Int) : Entity? {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Outside()
        }
        System.err.println("$x, $y, ${matrice[x][y]}")
        return matrice[x][y]
    }

    fun boxHitByBomb(x: Int, y: Int, power: Int) : Int {
        if (getAt(x, y) != null) {
            -1
        }
        val test = (x-power..x+power).count { getAt(it, 0) is Box }
        System.err.println("$test")
        return (x-power..x+power).count { getAt(it, y) is Box } + (y-power..y+power).count { getAt(x, it) is Box}
    }

    fun getNearestBox() : Box? {
        var dist = 1
        while (dist <= height || dist <= width) {
            for (i in player.x-dist..player.x+dist) {
                for (j in player.y-(dist-Math.abs(i))..player.y+(dist-Math.abs(i))) {
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