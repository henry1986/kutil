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

inline fun <T, K : Comparable<K>> List<T>.subListWithBinarySearch(start: K?, end: K?, crossinline selector: (T) -> K?): List<T> {
    val iStart = binarySearchByStart(start, selector)
    val iEnd = binarySearchByEnd(end, selector)
    return subList(iStart, iEnd)
}
