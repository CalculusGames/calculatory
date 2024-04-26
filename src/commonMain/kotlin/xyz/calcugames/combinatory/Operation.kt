package xyz.calcugames.combinatory

import kotlin.math.log
import kotlin.math.pow

/**
 * Represents an operation that can be performed on two numbers to get an output.
 */
interface Operation {

    /**
     * The operation to be performed.
     */
    val function: (Double, Double) -> Double

    /**
     * Invokes the operation on the two numbers.
     * @param a The first number.
     * @param b The second number.
     */
    operator fun invoke(a: Double, b: Double): Double = function(a, b)

    /**
     * Represents the arithmetic operations that can be performed.
     */
    enum class Artithmetic(override val function: (Double, Double) -> Double) : Operation {
        /**
         * Addition operation.
         */
        ADDITION({ a, b -> a + b }),

        /**
         * Subtraction operation.
         */
        SUBTRACTION({ a, b -> a - b }),

        /**
         * Multiplication operation.
         */
        MULTIPLICATION({ a, b -> a * b }),

        /**
         * Division operation.
         */
        DIVISION({ a, b -> a / b })
    }

    /**
     * Represents the power operations that can be performed.
     */
    enum class Power(override val function: (Double, Double) -> Double) : Operation {
        /**
         * Exponentiation operation.
         */
        EXPONENTIATION(exponentiation@{ a, b ->
            if (b == 0.0) return@exponentiation 1.0
            return@exponentiation a.pow(b)
        }),

        /**
         * Logarithm operation.
         */
        LOGARITHM(logarithm@{ a, b ->
            if (a == 0.0) return@logarithm 0.0
            if (b == 0.0) return@logarithm 0.0

            return@logarithm log(a, b)
        }),

        /**
         * Root operation.
         */
        ROOT(root@{ a, b ->
            if (a == 0.0) return@root 0.0
            if (b == 0.0) return@root 0.0

            return@root a.pow(1 / b)
        })
    }

    enum class Bitwise(override val function: (Double, Double) -> Double) : Operation {
        /**
         * Bitwise AND operation.
         */
        AND({ a, b -> a.toInt().and(b.toInt()).toDouble() }),

        /**
         * Bitwise OR operation.
         */
        OR({ a, b -> a.toInt().or(b.toInt()).toDouble() }),

        /**
         * Bitwise XOR operation.
         */
        EXCLUSIVE_OR({ a, b -> a.toInt().xor(b.toInt()).toDouble() }),

        /**
         * Bitwise Shift Left operation.
         */
        SHIFT_LEFT({ a, b -> a.toInt().shl(b.toInt()).toDouble() }),

        /**
         * Bitwise Shift Right operation.
         */
        SHIFT_RIGHT({ a, b -> a.toInt().shr(b.toInt()).toDouble() })
    }
}