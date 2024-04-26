package xyz.calcugames.combinatory.calculatory

import xyz.calcugames.combinatory.CombiMap
import xyz.calcugames.combinatory.Tile

/**
 * Represents a Path on a Combinatory [CombiMap].
 * @param map The map the path is on.
 * @param tiles The set of tiles in the path.
 */
data class Path(val map: CombiMap, val tiles: List<Tile>)

/**
 * Gets all possible paths on a map.
 * @param map The map to get the paths for
 * @param count The number of swipes to make
 * @return The set of all possible paths
 */
fun allPaths(map: CombiMap, count: Int): Set<Path> = allPaths0(map, count).map {
    val tiles = mutableListOf<Tile>()
    var c = map.start
    for (i in it) {
        tiles.add(c)
        c = map.getNeighbors(c)[i.toInt()]!!
    }

    Path(map, tiles)
}.toSet()

private fun allPaths0(map: CombiMap, count: Int): Set<ByteArray> {
    val set = mutableSetOf<ByteArray>()
    val c = mutableListOf<Byte>()

    fun run(tile: Tile) {
        val neighbors = map.getNeighbors(tile)

        if (neighbors[0] != null && c.lastOrNull() != 0.toByte()) { c.add(0); run(neighbors[0]!!) }
        if (neighbors[1] != null && c.lastOrNull() != 1.toByte()) { c.add(1); run(neighbors[1]!!) }
        if (neighbors[2] != null && c.lastOrNull() != 2.toByte()) { c.add(2); run(neighbors[2]!!) }
        if (neighbors[3] != null && c.lastOrNull() != 3.toByte()) { c.add(3); run(neighbors[3]!!) }

        if (c.size == count) {
            set.add(c.toByteArray())
            c.removeLastOrNull()
        }
    }

    run(map.start)
    return set
}