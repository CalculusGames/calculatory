@file:JvmName("Paths")

package xyz.calcugames.combinatory.calculatory

import korlibs.datastructure.fastArrayListOf
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import xyz.calcugames.combinatory.Operation
import kotlin.jvm.JvmName

/**
 * The directions a player can make on a grid. Represent up, down, left, and right.
 */
val directions = listOf(
    Pair(0, 1),
    Pair(0, -1),
    Pair(1, 0),
    Pair(-1, 0)
)

/**
 * Find all the possible values that can be obtained by moving on a grid.
 *
 * Worst-case time complexity is `O((4m)^k)`, where `m` is the size of [functions] and k is the number of tiles that can be visited from [sx] and [sy]. This significantly decreases, as the algorithm prunes duplicate results and utilizes
 * coroutines for parallel processing.
 *
 * For example, various testing on the [Mock Example](https://pl.kotl.in/t60amvt1e), which includes a 11x11 grid with 15 functions and 28 empty tiles, took `1.5s` and generated `73,118` values.
 * @param grid The grid to move on, represented as a 2D Double Array. Values of `0` represent an empty tile.
 * @param functions The operations to apply to the values.
 * @param sx The starting x coordinate.
 * @param sy The starting y coordinate.
 * @return A set of all the possible values that can be obtained by moving on the grid.
 * @see <a href="https://pl.kotl.in/t60amvt1e">Mock Example</a>
 */
suspend fun findValues(
    grid: Array<DoubleArray>,
    functions: List<Operation>,
    sx: Int,
    sy: Int
): Set<Double> = coroutineScope {
    val size = grid.size

    require(grid.isNotEmpty()) { "Grid must have at least one row" }
    require(grid[0].isNotEmpty()) { "Grid must have at least one column" }
    require(sx in grid.indices) { "Starting X Coordinate must be within the grid" }
    require(sy in grid[0].indices) { "Starting Y Coordinate must be within the grid" }
    require(grid.all { row -> row.size == size }) { "Grid must be square" }

    val channel = Channel<Double>()
    val visited = mutableSetOf<Pair<Int, Int>>()
    val values = mutableSetOf<Double>()

    suspend fun exploreValue(value: Double, cx: Int, cy: Int, operations: List<Operation>) {
        var hasMoved = false

        directions.forEach directions@{ (dx, dy) ->
            val nx = cx + dx
            val ny = cy + dy

            if (nx < 0.0 || nx >= size) return@directions
            if (ny < 0.0 || ny >= size) return@directions

            if (grid[nx][ny] == 0.0) return@directions
            if ((nx to ny) in visited) return@directions

            visited.add(nx to ny)
            val nv = grid[nx][ny]

            operations.forEach { o ->
                val newValue = o(value, nv)
                if (newValue in values) return@forEach

                values.add(newValue)
                channel.send(newValue)

                val remaining = operations.toMutableList()
                remaining.removeAt(remaining.indexOf(o))
                exploreValue(newValue, nx, ny, remaining)

                hasMoved = true
            }

            visited.remove(nx to ny)
        }

        if (!hasMoved || operations.isEmpty())
            channel.send(value)
    }

    val sv = grid[sx][sy]
    launch {
        exploreValue(sv, sx, sy, functions)
        channel.close()
    }

    for (value in channel)
        values.add(value)

    return@coroutineScope values
}