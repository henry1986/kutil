package org.daiv.util

import kotlin.test.Test
import kotlin.test.assertEquals

class BinarySearchByStart {
    @Test
    fun xBetween() {
        val list = listOf(1, 4, 6, 9, 15)
        val x = list.binarySearchByStart(5) { it }
        assertEquals(2, x)
    }

    @Test
    fun xOnNumber() {
        val list = listOf(1, 4, 6, 9, 15)
        val x = list.binarySearchByStart(6) { it }
        assertEquals(2, x)
    }
}

class BinarySearchByEnd {
    @Test
    fun xBetween() {
        val list = listOf(1, 4, 6, 9, 15)
        val x = list.binarySearchByEnd(10) { it }
        assertEquals(4, x)
    }

    @Test
    fun xOnNumber() {
        val list = listOf(1, 4, 6, 9, 15)
        val x = list.binarySearchByEnd(9) { it }
        assertEquals(4, x)
    }
}

class SubListByBinarySearch {
    @Test
    fun xBetween() {
        val list = listOf(1, 4, 6, 9, 15)
        val x = list.subListWithBinarySearch(5, 10) { it }
        assertEquals(listOf(6, 9), x)
    }

    @Test
    fun xOnNumber() {
        val list = listOf(1, 4, 6, 9, 15)
        val x = list.subListWithBinarySearch(4, 9) { it }
        assertEquals(listOf(4, 6, 9), x)
    }
}