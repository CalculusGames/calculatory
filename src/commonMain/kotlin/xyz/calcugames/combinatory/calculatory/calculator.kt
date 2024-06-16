package xyz.calcugames.combinatory.calculatory

import xyz.calcugames.combinatory.CombiMap
import xyz.calcugames.combinatory.Operation

/**
 * Calculates all the possible number combinations given a map and operations.
 * @param map The map to calculate.
 */
fun combinations(map: CombiMap): Set<Double> =
    combinations(allPaths(map, map.operations.size), map.operations.flatMap { (op, count) -> List(count) { op } })

/**
 * Calculates all the possible number combinations given a set of paths and operations.
 * @param paths The paths to calculate.
 * @param operations The operations to perform.
 * @throws IllegalArgumentException if the number of tiles in a path is not equal to the number of operations
 */
fun combinations(paths: Set<Path>, operations: Collection<Operation>): Set<Double> {
    val results = mutableSetOf<Double>()
    val permutations = permutations(operations.toList())

    for (path in paths) {
        for (perm in permutations) {
            val result = path.calculate(perm)
            results.add(result)
        }
    }

    return results
}

/**
 * Calculates the value of a path given a list of operations.
 * @param operations The operations to perform.
 * @return The value of the path.
 */
fun Path.calculate(operations: List<Operation>): Double {
    val start = tiles[0]
    return tiles.zip(operations).fold(start.value) { acc, (tile, op) -> op(acc, tile.value) }
}

private fun <T> permutations(list: List<T>): Set<List<T>> {
    if (list.size == 1) return setOf(list)

    val perms = mutableSetOf<List<T>>()
    for (i in list.indices) {
        val element = list[i]
        val rest = list.toMutableList()
        rest.removeAt(i)
        perms += permutations(rest).map { it + element }
    }

    return perms
}