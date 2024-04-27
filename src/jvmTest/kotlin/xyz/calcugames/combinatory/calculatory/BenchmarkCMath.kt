package xyz.calcugames.combinatory.calculatory

import kotlin.test.Test
import kotlin.time.measureTime

class BenchmarkCMath {

    @Test
    fun benchmarkFactorial() {
        val e1 = measureTime {
            for (i in 0..1000) {
                factorial(i)
            }
        }
        println("Factorial 1a: ${e1.inWholeMilliseconds}ms / 1,000")

        val e2 = measureTime {
            for (i in 0..1000) {
                factorial(i)
            }
        }
        println("Factorial 1b: ${e2.inWholeMilliseconds}ms / 1,000")

        val e3 = measureTime {
            for (i in 1000..11000) {
                factorial(i)
            }
        }
        println("Factorial 2a: ${e3.inWholeMilliseconds}ms / 10,000")

        val e4 = measureTime {
            for (i in 1000..11000) {
                factorial(i)
            }
        }
        println("Factorial 2b: ${e4.inWholeMilliseconds}ms / 10,000")
    }

    @Test
    fun benchmarkPermutation() {
        val e1 = measureTime {
            for (i in 0..100) {
                permutation(100, i)
            }
        }
        println("Permutation 1a: ${e1.inWholeMilliseconds}ms / 100")

        val e2 = measureTime {
            for (i in 0..100) {
                permutation(100, i)
            }
        }
        println("Permutation 1b: ${e2.inWholeMilliseconds}ms / 100")

        val e3 = measureTime {
            for (i in 100..1100) {
                permutation(1100, i)
            }
        }
        println("Permutation 2a: ${e3.inWholeMilliseconds}ms / 1,000")

        val e4 = measureTime {
            for (i in 100..1100) {
                permutation(1100, i)
            }
        }
        println("Permutation 2b: ${e4.inWholeMilliseconds}ms / 1,000")
    }

}