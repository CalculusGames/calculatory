package xyz.calcugames.combinatory.calculatory

import kotlinx.coroutines.runBlocking
import xyz.calcugames.combinatory.Operation
import kotlin.test.Test
import kotlin.time.measureTime

class BenchmarkPaths {

    @Test
    fun benchmark() = runBlocking {
        val g1 = arrayOf(
            doubleArrayOf(1.0, 2.0, 3.0),
            doubleArrayOf(6.0, 5.0, 4.0),
            doubleArrayOf(7.0, 8.0, 9.0)
        )
        val o1 = listOf(
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION
        )

        println("Functions #1: ${o1.size}")
        val e1 = measureTime {
            val values = findValues(g1, o1, 0, 1)

            println("Values #1: ${values.size}")
            println("Min Value #1: ${values.minOrNull()}")
            println("Max Value #1: ${values.maxOrNull()}")
        }
        println("Time #1: ${e1.inWholeMilliseconds}ms\n")

        val g2 = arrayOf(
            doubleArrayOf(1.0, 2.0, 0.0, 3.0, 1.0),
            doubleArrayOf(6.0, 0.0, 0.0, 5.0, 1.0),
            doubleArrayOf(7.0, 8.0, 2.0, 6.0, 2.0),
            doubleArrayOf(0.0, 4.0, 1.0, 0.0, 5.0),
            doubleArrayOf(0.0, 1.0, 2.0, 0.0, 0.0)
        )
        val o2 = listOf(
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION
        )

        println("Functions #2: ${o2.size}")
        val e2 = measureTime {
            val values = findValues(g2, o2, 2, 2)

            println("Values #2: ${values.size}")
            println("Min Value #2: ${values.minOrNull()}")
            println("Max Value #2: ${values.maxOrNull()}")
        }
        println("Time #2: ${e2.inWholeMilliseconds}ms\n")

        val g3 = arrayOf(
            doubleArrayOf(1.0, 2.0, 0.0, 3.0, 1.0, 6.0, 7.0),
            doubleArrayOf(6.0, 0.0, 1.0, 5.0, 1.0, 5.0, 8.0),
            doubleArrayOf(7.0, 8.0, 2.0, 6.0, 2.0, 4.0, 9.0),
            doubleArrayOf(0.0, 4.0, 1.0, 0.0, 5.0, 3.0, 1.0),
            doubleArrayOf(0.0, 1.0, 2.0, 9.0, 8.0, 2.0, 0.0),
            doubleArrayOf(0.0, 3.0, 4.0, 4.0, 3.0, 1.0, 2.0),
            doubleArrayOf(5.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
        )
        val o3 = listOf(
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.DIVISION
        )

        println("Functions #3: ${o3.size}")
        val e3 = measureTime {
            val values = findValues(g3, o3, 3, 3)

            println("Values #3: ${values.size}")
            println("Min Value #3: ${values.minOrNull()}")
            println("Max Value #3: ${values.maxOrNull()}")
        }
        println("Time #3: ${e3.inWholeMilliseconds}ms\n")

        val g4 = arrayOf(
            doubleArrayOf(1.0, 2.0, 0.0, 3.0, 1.0, 6.0, 7.0, 8.0, 1.0),
            doubleArrayOf(6.0, 0.0, 1.0, 5.0, 1.0, 5.0, 8.0, 9.0, 2.0),
            doubleArrayOf(0.0, 8.0, 0.0, 6.0, 0.0, 4.0, 0.0, 1.0, 3.0),
            doubleArrayOf(0.0, 4.0, 1.0, 0.0, 5.0, 3.0, 1.0, 2.0, 4.0),
            doubleArrayOf(2.0, 1.0, 2.0, 9.0, 3.0, 2.0, 0.0, 0.0, 5.0),
            doubleArrayOf(0.0, 3.0, 4.0, 4.0, 3.0, 1.0, 2.0, 1.0, 6.0),
            doubleArrayOf(5.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0, 0.0, 7.0),
            doubleArrayOf(2.0, 4.0, 3.0, 2.0, 1.0, 0.0, 0.0, 0.0, 8.0),
            doubleArrayOf(3.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0, 0.0, 9.0)
        )
        val o4 = listOf(
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.DIVISION,
            Operation.Arithmetic.DIVISION,
        )

        println("Functions #4: ${o4.size}")
        val e4 = measureTime {
            val values = findValues(g4, o4, 4, 4)

            println("Values #4: ${values.size}")
            println("Min Value #4: ${values.minOrNull()}")
            println("Max Value #4: ${values.maxOrNull()}")
        }
        println("Time #4: ${e4.inWholeMilliseconds}ms\n")

        val g5 = arrayOf(
            doubleArrayOf(3.0, 1.0, 0.0, 0.0, 0.0, 4.0, 5.0, 8.0, 2.0, 1.0, 0.0),
            doubleArrayOf(0.0, 1.0, 0.0, 7.0, 0.0, 5.0, 6.0, 9.0, 3.0, 2.0, 0.0),
            doubleArrayOf(0.0, 2.0, 0.0, 2.0, 0.0, 6.0, 7.0, 0.0, 4.0, 3.0, 2.0),
            doubleArrayOf(0.0, 3.0, 0.0, 10.0, 3.0, 7.0, 8.0, 1.0, 5.0, 4.0, 0.0),
            doubleArrayOf(0.0, 4.0, 0.0, 2.0, 1.0, 8.0, 9.0, 2.0, 6.0, 5.0, 0.0),
            doubleArrayOf(3.0, 5.0, 3.0, 3.0, 2.0, 9.0, 0.0, 3.0, 7.0, 6.0, 0.0),
            doubleArrayOf(0.0, 6.0, 7.0, 0.0, 3.0, 0.0, 1.0, 4.0, 8.0, 7.0, 0.0),
            doubleArrayOf(2.0, 7.0, 3.0, 3.0, 4.0, 1.0, 10.0, 5.0, 9.0, 8.0, 0.0),
            doubleArrayOf(1.0, 8.0, 0.0, 1.0, 5.0, 2.0, 3.0, 6.0, 0.0, 11.0, 0.0),
            doubleArrayOf(8.0, 9.0, 0.0, 0.0, 6.0, 3.0, 4.0, 7.0, 1.0, 0.0, 0.0),
            doubleArrayOf(6.0, 0.0, 0.0, 0.0, 7.0, 4.0, 5.0, 8.0, 2.0, 1.0, 0.0)
        )
        val o5 = listOf(
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.ADDITION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.SUBTRACTION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.MULTIPLICATION,
            Operation.Arithmetic.DIVISION,
            Operation.Arithmetic.DIVISION,
        )

        println("Functions #5: ${o5.size}")
        val e5 = measureTime {
            val values = findValues(g5, o5, 5, 5)

            println("Values #5: ${values.size}")
            println("Min Value #5: ${values.minOrNull()}")
            println("Max Value #5: ${values.maxOrNull()}")
        }
        println("Time #5: ${e5.inWholeMilliseconds}ms\n")
    }

}