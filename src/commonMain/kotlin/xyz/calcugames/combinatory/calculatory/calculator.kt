package xyz.calcugames.combinatory.calculatory

import xyz.calcugames.combinatory.CombiMap
import xyz.calcugames.combinatory.Operation

/**
 * Calculates all the possible number combinations given a map and operations.
 * @param map The map to calculate.
 */
fun combinations(map: CombiMap): Set<Double> = combinations(allPaths(map, map.operations.size), map.operations.flatMap { (op, count) -> List(count) { op } })

/**
 * Calculates all the possible number combinations given a set of paths and operations.
 * @param paths The paths to calculate.
 * @param operations The operations to perform.
 * @throws IllegalArgumentException if the number of tiles in a path is not equal to the number of operations
 */
fun combinations(paths: Set<Path>, operations: Collection<Operation>): Set<Double> {
    val results = mutableSetOf<Double>()

    for (path in paths) {
        if (path.tiles.size != operations.size) throw IllegalArgumentException("The number of tiles in the path must be equal to the number of operations")

        var v = path.tiles.first().value
        for ((i, tile) in path.tiles.drop(1).withIndex())
            v = operations.elementAt(i)(v, tile.value)

        results.add(v)
    }

    return results
}