package xyz.calcugames.combinatory.calculatory

import xyz.calcugames.combinatory.CombiMap
import xyz.calcugames.combinatory.Tile

/**
 * Represents a Path on a Combinatory [CombiMap].
 * @param tiles The set of tiles in the path.
 */
data class Path(val tiles: List<Tile>) {

    override fun toString(): String
        = tiles.joinToString(" -> ") { "(${it.x}, ${it.y})" }

}

/**
 * Gets all possible paths on a map.
 * @param map The map to get the paths for
 * @param count The number of swipes to make
 * @return The set of all possible paths
 */
fun allPaths(map: CombiMap, count: Int): Set<Path> =
    allPaths0(map, count).map(::Path).toSet()

private fun allPaths0(map: CombiMap, count: Int): List<List<Tile>> {
    val paths = mutableListOf<List<Tile>>()
    val start = map.start
    val valid = mutableSetOf<Tile>()

    for (x in 0 until map.size)
        for (y in 0 until map.size)
            map[x, y]?.let { valid.add(it) }

    fun dfs(currentPath: MutableList<Tile>, currentTile: Tile): List<List<Tile>> {
        if (currentPath.size == count) {
            return listOf(ArrayList(currentPath))
        }

        val current = mutableListOf<List<Tile>>()
        val neighbors = map.getNeighbors(currentTile.x, currentTile.y)
        for (neighbor in neighbors) {
            if (neighbor != null && neighbor !in currentPath) {
                currentPath.add(neighbor)
                current.addAll(dfs(currentPath, neighbor))
                currentPath.removeAt(currentPath.size - 1)
            }
        }
        return current
    }

    paths.addAll(dfs(mutableListOf(start), start))

    return paths
}