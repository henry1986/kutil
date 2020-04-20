package org.daiv.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

class BinarySearchTest :
        Spek({
                 describe("CompressedManager functions") {
                     on("binarySearchByStart") {
                         it("x between ") {
                             val list = listOf(1, 4, 6, 9, 15)
                             val x = list.binarySearchByStart(5) { it }
                             assertEquals(2, x)
                         }
                         it("x on number ") {
                             val list = listOf(1, 4, 6, 9, 15)
                             val x = list.binarySearchByStart(6) { it }
                             assertEquals(2, x)
                         }
                     }
                     on("binarySearchByEnd") {
                         it("x between ") {
                             val list = listOf(1, 4, 6, 9, 15)
                             val x = list.binarySearchByEnd(10) { it }
                             assertEquals(4, x)
                         }
                         it("x on number ") {
                             val list = listOf(1, 4, 6, 9, 15)
                             val x = list.binarySearchByEnd(9) { it }
                             assertEquals(4, x)
                         }
                     }
                     on("subListByBinarySearch") {
                         it("x between ") {
                             val list = listOf(1, 4, 6, 9, 15)
                             val x = list.subListWithBinarySearch(5, 10) { it }
                             assertEquals(listOf(6, 9), x)
                         }
                         it("x on number ") {
                             val list = listOf(1, 4, 6, 9, 15)
                             val x = list.subListWithBinarySearch(4, 9) { it }
                             assertEquals(listOf(4, 6, 9), x)
                         }
                     }
                 }
             })