package org.daiv.util

fun <T : Any?, R : Any?> List<T>.innerRec(i: Int, ret: List<R>, map: (Int, T, List<R>) -> R): List<R> {
    if (i < size) {
        return innerRec(i + 1, ret + map(i, get(i), ret), map)
    }
    return ret
}

fun <T : Any?, R : Any?> List<T>.innerRecFlat(i: Int, ret: List<R>, map: (Int, T, List<R>) -> List<R>): List<R> {
    if (i < size) {
        return innerRecFlat(i + 1, ret + map(i, get(i), ret), map)
    }
    return ret
}

fun <T : Any?, R : Any?> List<T>.innerWhile(i: Int, initList: List<R>, map: (Int, T, List<R>) -> R): List<R> {
    val ret: MutableList<R> = initList.toMutableList()
    while (i < size) {
        val r = map(i, get(i), ret)
        ret.add(r)
    }
    return ret.toList()
}

fun <T : Any?, R : Any?> List<T>.innerWhileFlat(i: Int, initList: List<R>, map: (Int, T, List<R>) -> List<R>): List<R> {
    val ret: MutableList<R> = initList.toMutableList()
    while (i < size) {
        val r = map(i, get(i), ret)
        ret.addAll(r)
    }
    return ret.toList()
}

fun <T : Any?, R : Any?> List<T>.recIndexed(listInit: List<R> = emptyList(), rec: Boolean = false, map: (Int, T, List<R>) -> R): List<R> {
    if (isEmpty()) {
        return emptyList()
    }
    return if (rec) innerWhile(0, listInit, map) else innerRec(0, listInit, map)
}

fun <T : Any?, R : Any?> List<T>.rec(listInit: List<R> = emptyList(), rec: Boolean = false, map: (T) -> R): List<R> {
    if (isEmpty()) {
        return emptyList()
    }
    val xMap: (Int, T, List<R>) -> R = { i, t, l -> map(t) }
    return if (rec) innerWhile(0, listInit, xMap) else innerRec(0, listInit, xMap)
}

fun <T, R> List<T>.flatRecIndexed(listInit: List<R> = emptyList(), rec: Boolean = false, map: (Int, T, List<R>) -> List<R>): List<R> {
    if (isEmpty()) {
        return emptyList()
    }
    return if (rec) innerWhileFlat(0, listInit, map) else innerRecFlat(0, listInit, map)
}

fun <T, R> List<T>.flatRec(listInit: List<R> = emptyList(), rec: Boolean = false, map: (T) -> List<R>): List<R> {
    if (isEmpty()) {
        return emptyList()
    }
    val xMap: (Int, T, List<R>) -> List<R> = { i, t, l -> map(t) }
    return if (rec) innerWhileFlat(0, listInit, xMap) else innerRecFlat(0, listInit, xMap)
}


fun <T : Any?, R : Any?> List<T>.innerRec(i: Int, ret: List<R>, map: (Int, T, T, List<R>) -> R): List<R> {
    if (i < size) {
        return innerRec(i + 1, ret + map(i, get(i), get(i - 1), ret), map)
    }
    return ret
}

fun <T : Any?, R : Any?> List<T>.innerWhile(i: Int, listInit: List<R> = emptyList(), map: (Int, T, T, List<R>) -> R): List<R> {
    val ret: MutableList<R> = listInit.toMutableList()
    while (i < size) {
        val r = map(i, get(i), get(i - 1), ret)
        ret.add(r)
    }
    return ret.toList()
}

/**
 * use map with the running index, the current value, the previous value and the created list
 */
fun <T : Any?, R : Any?> List<T>.rec(listInit: List<R> = emptyList(), rec: Boolean = false, map: (Int, T, T, List<R>) -> R): List<R> {
    if (size < 2) {
        return emptyList()
    }
    return if (rec) innerRec(1, listInit, map) else innerWhile(1, listInit, map)
}

fun <K, V> Map.Entry<K, V>.toPair() = key to value
fun <K, V> List<Map<K, V>>.flatten() = flatRec { it.map { it.toPair() } }.toMap()
