package xyz.calcugames.combinatory.calculatory

import xyz.calcugames.combinatory.CombiMap
import xyz.calcugames.combinatory.Tile

/**
 * Represents a Path on a Combinatory [CombiMap].
 * @param tiles The set of tiles in the path.
 */
data class Path(val tiles: List<Tile>)

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
    val visited = Array(map.size) { BooleanArray(map.size) }
    val start = map.start
    val (sx, sy) = start

    fun dfs(current: MutableList<Tile>, x: Int, y: Int) {
        if (current.size == count) {
            paths.add(current)
            return
        }

        val neighbors = map.getNeighbors(x, y)
        for (neighbor in neighbors.filterNotNull().filter { !visited[it.x][it.y] }) {
            val (nx, ny) = neighbor

            visited[nx][ny] = true
            current.add(neighbor)
            dfs(current, nx, ny)
            current.removeLast()
            visited[nx][ny] = false
        }
    }

    visited[sx][sy] = true
    dfs(mutableListOf(start), sx, sy)
    visited[sx][sy] = false

    return paths
}