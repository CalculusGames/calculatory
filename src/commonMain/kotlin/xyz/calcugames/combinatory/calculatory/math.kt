@file:JvmName("CMath")

package xyz.calcugames.combinatory.calculatory

import kotlin.jvm.JvmName

private val cache = mutableMapOf<Int, Double>()

/**
 * Calculates the factorial of a number.
 * @param n The number to calculate the factorial for
 */
fun factorial(n: Int): Double {
    if (n == 0) return 1.0

    if (n !in cache) {
        var r = 1.0
        for (i in 2..n)
            r *= i

        cache[n] = r
        return r
    }

    return cache[n]!!
}

/**
 * Calculates the permutation of n and r.
 * @param n The number of elements
 * @param r The number of elements to choose
 * @return The permutation of n and r
 */
fun permutation(n: Int, r: Int): Double {
    if (r == 0) return 1.0
    if (r == 1) return n.toDouble()

    return factorial(n) / factorial(n - r)
}

/**
 * Calculates the combination of n and r.
 * @param n The number of elements
 * @param r The number of elements to choose
 * @return The combination of n and r
 */
fun combination(n: Int, r: Int): Double {
    if (r == 0 || r == n) return 1.0
    if (r == 1) return n.toDouble()

    return permutation(n, r) / factorial(r)
}