package xyz.calcugames.combinatory.calculatory

import xyz.calcugames.combinatory.Tile

/**
 * Centers the tiles in the grid.
 * @param size The size of the grid.
 * @return This iterable with the tiles centered.
 */
fun Iterable<Tile>.center(size: Int): Iterable<Tile> {
    if (!iterator().hasNext()) return this

    val minX = minOf { it.x }; val maxX = maxOf { it.x }
    val minY = minOf { it.y }; val maxY = maxOf { it.y }

    val width = maxX - minX + 1
    val height = maxY - minY + 1

    val offsetX = (size - width) / 2 - minX
    val offsetY = (size - height) / 2 - minY

    for (tile in this) {
        tile.x += offsetX
        tile.y += offsetY
    }

    return this
}

/**
 * Removes the inaccessible tiles from the grid.
 * @param startX The x coordinate of the starting tile.
 * @param startY The y coordinate of the starting tile.
 * @param size The size of the grid.
 * @return A list of tiles with the inaccessible tiles removed.
 */
fun Iterable<Tile>.removeInaccessibleTiles(startX: Int, startY: Int, size: Int): List<Tile> {
    if (!iterator().hasNext() || startX !in 0 until size || startY !in 0 until size) return this.toList()

    val queue: ArrayDeque<Pair<Int, Int>> = ArrayDeque()
    val visited = mutableSetOf<Pair<Int, Int>>()
    val map = associateBy { Pair(it.x, it.y) }

    val start = map[startX to startY]
    if (start != null && start.value != 0.0) {
        queue.add(startX to startY)
        visited.add(startX to startY)
    }

    while (queue.isNotEmpty()) {
        val (cx, cy) = queue.removeFirst()

        for ((dx, dy) in directions) {
            val newX = cx + dx
            val newY = cy + dy

            val newPos = Pair(newX, newY)
            if (newX in 0 until size && newY in 0 until size && newPos !in visited) {
                val newTile = map[newPos]
                if (newTile != null && newTile.value != 0.0) {
                    visited.add(newPos)
                    queue.add(newPos)
                }
            }
        }
    }

    val accessible = map { tile ->
        if (visited.contains(Pair(tile.x, tile.y))) tile else tile.copy(value = 0.0)
    }

    return accessible
}