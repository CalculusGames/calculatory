package xyz.calcugames.combinatory.calculatory

import kotlin.test.Test
import kotlin.test.assertEquals

class TestCMath {

    @Test
    fun testFactorial() {
        assertEquals(1.0, factorial(0))
        assertEquals(1.0, factorial(1))
        assertEquals(2.0, factorial(2))
        assertEquals(6.0, factorial(3))
        assertEquals(24.0, factorial(4))
        assertEquals(120.0, factorial(5))
        assertEquals(720.0, factorial(6))
        assertEquals(5040.0, factorial(7))
        assertEquals(40320.0, factorial(8))
        assertEquals(362880.0, factorial(9))
        assertEquals(3628800.0, factorial(10))
    }

    @Test
    fun testPermutation() {
        assertEquals(1.0, permutation(0, 0))
        assertEquals(1.0, permutation(1, 0))
        assertEquals(1.0, permutation(1, 1))
        assertEquals(2.0, permutation(2, 1))
        assertEquals(6.0, permutation(3, 2))
        assertEquals(24.0, permutation(4, 3))
        assertEquals(120.0, permutation(5, 4))
        assertEquals(720.0, permutation(6, 5))
        assertEquals(5040.0, permutation(7, 6))
        assertEquals(40320.0, permutation(8, 7))
        assertEquals(362880.0, permutation(9, 8))
        assertEquals(3628800.0, permutation(10, 9))
    }

    @Test
    fun testCombination() {
        assertEquals(1.0, combination(0, 0))
        assertEquals(1.0, combination(1, 0))
        assertEquals(1.0, combination(1, 1))
        assertEquals(2.0, combination(2, 1))
        assertEquals(3.0, combination(3, 2))
        assertEquals(6.0, combination(4, 2))
        assertEquals(10.0, combination(5, 2))
        assertEquals(15.0, combination(6, 2))
        assertEquals(21.0, combination(7, 2))
        assertEquals(28.0, combination(8, 2))
        assertEquals(36.0, combination(9, 2))
        assertEquals(45.0, combination(10, 2))
    }

}