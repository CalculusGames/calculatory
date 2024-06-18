package xyz.calcugames.combinatory.calculatory

import xyz.calcugames.combinatory.CombiMap
import xyz.calcugames.combinatory.Operation
import xyz.calcugames.combinatory.Tile

class MockCombiMap(
    override val size: Int,
    override val operations: Map<Operation, Int>,
    startX: Int,
    startY: Int,
    apply: MockCombiMap.() -> Unit = {}
) : CombiMap {

    val map: Array<Array<Double?>> = Array(size) { arrayOfNulls(size) }

    override val start: Tile
    override val tiles: Set<Tile>
        get() = map.flatMapIndexed { x, row -> row.filterNotNull().mapIndexed { y, value -> MockTile(value, x, y) } }.toSet()

    init {
        apply()
        start = MockTile(map[startX][startY]!!, startX, startY)
    }

    operator fun set(x: Int, y: Int, value: Double) {
        map[x][y] = value
    }

    operator fun set(xr: IntRange, yr: IntRange, value: Double) {
        for (x in xr) {
            for (y in yr) {
                map[x][y] = value
            }
        }
    }

    operator fun set(xr: IntRange, y: Int, value: Double) {
        for (x in xr) {
            map[x][y] = value
        }
    }

    operator fun set(x: Int, yr: IntRange, value: Double) {
        for (y in yr) {
            map[x][y] = value
        }
    }

    override fun toString(): String {
        return "\n" + map.joinToString("\n") { row -> row.joinToString(" ") { it?.format ?: "X" } }
    }

    private val Number.format: String
        get() {
            if (toDouble() == toInt().toDouble()) {
                return toInt().toString()
            }

            return toString()
        }
}

class MockTile(override val value: Double, override val x: Int, override val y: Int) : Tile {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MockTile) return false

        if (value != other.value) return false
        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + x
        result = 31 * result + y
        return result
    }
}