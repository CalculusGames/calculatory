package xyz.calcugames.combinatory.storage

/**
 * Creates a range of numbers.
 * @param start The starting number.
 * @param end The ending number.
 * @param step The step between each number.
 * @return The range of numbers.
 */
fun range(start: Double, end: Double, step: Double): Iterable<Double> {
    val range = mutableListOf<Double>()
    var current = start

    while (current <= end) {
        range.add(current)
        current += step
    }

    return range
}