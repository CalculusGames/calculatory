package xyz.calcugames.combinatory.calculatory

import xyz.calcugames.combinatory.Operation
import kotlin.test.Test
import kotlin.time.measureTime

class BenchmarkPathfinder {

    @Test
    fun benchmarkPathfinder5x5() {
        val m1 = MockCombiMap(5, mapOf(Operation.Artithmetic.ADDITION to 4), 0, 0) {
            this[0, 0..4] = 1.0
        }
        println("Map 1a: $m1")
        val e1 = measureTime {
            val paths = allPaths(m1, 4)
            println("Paths 1a: $paths")
        }
        val c1 = measureTime {
            val combinations = combinations(m1)
            println("Combinations 1a: $combinations")
        }
        println("Pathfinder 1a: ${e1.inWholeMilliseconds}ms")
        println("Calculator 1a: ${c1.inWholeMilliseconds}ms")

        val m2 = MockCombiMap(5, mapOf(
            Operation.Artithmetic.ADDITION to 6,
            Operation.Artithmetic.SUBTRACTION to 2
        ), 2, 1) {
            this[0, 0..4] = 1.0
            this[1, 0..4] = 2.0
            this[2, 0..4] = 1.0
            this[3, 0..4] = 3.0
            this[4, 0..4] = 1.0
        }
        println("Map 2a: $m2")
        val e2 = measureTime {
            val paths = allPaths(m2, 4)
            println("Paths 2a: $paths")
        }
        val c2 = measureTime {
            val combinations = combinations(m2)
            println("Combinations 2a: $combinations")
        }
        println("Pathfinder 2a: ${e2.inWholeMilliseconds}ms")
        println("Calculator 2a: ${c2.inWholeMilliseconds}ms")

        val m3 = MockCombiMap(5, mapOf(
            Operation.Artithmetic.ADDITION to 2,
            Operation.Artithmetic.SUBTRACTION to 2,
            Operation.Artithmetic.MULTIPLICATION to 4
        ), 4, 2) {
            this[0, 2..4] = 1.0
            this[1, 0..2] = 2.0
            this[2, 0..1] = 4.0
            this[3, 0..2] = 3.0
            this[4, 1..3] = 1.0
        }
        println("Map 3a: $m3")
        val e3 = measureTime {
            val paths = allPaths(m3, 4)
            println("Paths 3a: $paths")
        }
        val c3 = measureTime {
            val combinations = combinations(m3)
            println("Combinations 3a: $combinations")
        }
        println("Pathfinder 3a: ${e3.inWholeMilliseconds}ms")
        println("Calculator 3a: ${c3.inWholeMilliseconds}ms")

        val m4 = MockCombiMap(5, mapOf(
            Operation.Artithmetic.ADDITION to 1,
            Operation.Artithmetic.SUBTRACTION to 3,
            Operation.Artithmetic.MULTIPLICATION to 3
        ), 4, 2) {
            this[0, 1..4] = 1.0
            this[1, 1..2] = 2.0
            this[2, 1] = 4.0
            this[3, 1..2] = 5.0
            this[4, 1..3] = 1.0
        }
        println("Map 4a: $m4")
        val e4 = measureTime {
            val paths = allPaths(m4, 4)
            println("Paths 4a: $paths")
        }
        val c4 = measureTime {
            val combinations = combinations(m4)
            println("Combinations 4a: $combinations")
        }
        println("Pathfinder 4a: ${e4.inWholeMilliseconds}ms")
        println("Calculator 4a: ${c4.inWholeMilliseconds}ms")
    }

}