package org.daiv.util

import kotlin.test.Test
import kotlin.test.assertEquals


class RegistererTest {
    val logger = mu.KotlinLogging.logger {}

    @Test
    fun rec() {
        val map1 = mapOf(1 to "Hello", 2 to "World")
        val map2 = mapOf(3 to "HelloNow", 4 to "WorldNow")
        val list = listOf(map1, map2)
        val flatList = list.flatten()
        assertEquals(mapOf(1 to "Hello", 2 to "World", 3 to "HelloNow", 4 to "WorldNow"), flatList)
    }

    @Test
    fun reorder() {
        val list = listOf(mapOf(1 to "1a", 2 to "2a", 3 to "3a"), mapOf(1 to "1b", 2 to "2b", 3 to "3b"))
        val expect = mapOf(1 to listOf("1a", "1b"), 2 to listOf("2a", "2b"), 3 to listOf("3a", "3b"))
        assertEquals(expect, list.reorder())
    }
}
