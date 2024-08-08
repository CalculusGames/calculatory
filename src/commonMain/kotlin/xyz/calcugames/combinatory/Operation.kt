package xyz.calcugames.combinatory

/**
 * Represents an operation that can be performed on two numbers to get an output.
 */
interface Operation {

    /**
     * The name of the operation.
     */
    val name: String

    /**
     * The ordinal ID of the operation, according to its subdivision.
     */
    val ordinal: Int

    /**
     * The operation to be performed.
     */
    val function: (Double, Double) -> Double

    /**
     * The internal ID of the operation. This is unique to each operation.
     */
    val id: Int

    /**
     * Invokes the operation on the two numbers.
     * @param a The first number.
     * @param b The second number.
     */
    operator fun invoke(a: Double, b: Double): Double = function(a, b)

    /**
     * Represents the arithmetic operations that can be performed.
     */
    enum class Arithmetic(override val function: (Double, Double) -> Double) : Operation {
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

        ;

        override val id: Int
            get() = ordinal
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

        ;

        override val id: Int
            get() = ordinal + Arithmetic.entries.size
    }
}