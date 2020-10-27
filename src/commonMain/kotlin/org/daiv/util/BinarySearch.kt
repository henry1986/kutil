package org.daiv.util

/**
 * returns the specified index inclusive [key]
 */
inline fun <T, K : Comparable<K>> List<T>.binarySearchByStart(key: K?, crossinline selector: (T) -> K?): Int {
    val i = binarySearchBy(key) { selector(it) }
    return if (i < 0) -i - 1 else i
}

/**
 * returns the specified index inclusive [key]
 */
inline fun <T, K : Comparable<K>> List<T>.binarySearchByEnd(key: K?, crossinline selector: (T) -> K?): Int {
    val i = binarySearchBy(key) { selector(it) }
    return if (i < 0) -i - 1 else i + 1
}

inline fun <T, K : Comparable<K>> List<T>.subListWithBinarySearch(
    start: K?,
    end: K?,
    crossinline selector: (T) -> K?
): List<T> {
    val iStart = binarySearchByStart(start, selector)
    val iEnd = binarySearchByEnd(end, selector)
    return subList(iStart, iEnd)
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun String.add(string: String, max: Int, i: Int = 0): String {
    return if (i < max) {
        "$this$string".add(string, max, i + 1)
    } else {
        this
    }
}

fun Double.toString(decimals: Int): String {
    val s = toString()
    val l = s.length
    val indexOf = s.indexOf('.')
    val missingNulls = l - decimals
    if (indexOf <missingNulls) {
        return round(decimals).toString()
    } else {
        if(indexOf == -1){
            return "$s.".add("0", decimals)
        }
        return s.add("0", missingNulls)
    }
}
//fun Double.toString(decimals: Int): String {
//    var multiplier = 1.0
//    repeat(decimals) { multiplier *= 10 }
//    return kotlin.math.round(this * multiplier) / multiplier
//}
