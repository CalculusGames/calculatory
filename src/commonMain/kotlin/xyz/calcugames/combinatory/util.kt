package xyz.calcugames.combinatory

import kotlin.math.log10
import kotlin.math.pow

// Extensions

val Number.asFormat: String
    get() = when (this) {
        is Double, is Float -> {
            val l = toLong()
            val tenth = (this.toDouble() - l) * 10

            if (tenth < 1.0)
                l.toString()
            else
                "$l.${tenth.toLong()}"
        }
        else -> toString()
    }

val Number.comma: String
    get() = asFormat.replace(Regex("(\\d)(?=(\\d{3})+(?!\\d))"), "$1,")

private const val SUFFIXES = "KMBTQEXSON"

fun Number.withSuffix(): String {
    val num = toDouble()
    if (num.isNaN()) return num.toString()
    if (num < 0) return "-" + (-num).withSuffix()
    if (num < 1000) return asFormat

    val index = (log10(num) / 3).toInt()
    val suffix = SUFFIXES[index - 1]

    val n = num / 10.0.pow(index * 3)
    return "${n.asFormat}$suffix"
}

fun String.fromSuffix(): Double {
    val n0 = toDoubleOrNull()
    if (n0 != null) return n0

    val n = substring(0, length - 1).toDouble()
    val suffix = SUFFIXES.indexOf(last()) + 1

    return n * 10.0.pow(suffix * 3)
}