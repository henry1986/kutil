package org.daiv.util

import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

class RegistererTest : Spek({
                                describe("Registerer") {
                                    val logger = mu.KotlinLogging.logger {}
                                    on("test") {
                                        it("listener execution") {
                                            logger.trace { "testRegisterer" }
                                            val test = TestRegisterer()
                                            val testListener = mockk<TestListener>(relaxed = true)
                                            test.register(testListener)
                                            test.itsHappening()
                                            verify { testListener.event() }
                                        }
                                    }
                                    on("test rec") {
                                        it("rec") {
                                            val map1 = mapOf(1 to "Hello", 2 to "World")
                                            val map2 = mapOf(3 to "HelloNow", 4 to "WorldNow")
                                            val list = listOf(map1, map2)
                                            val flatList = list.flatten()
                                            assertEquals(mapOf(1 to "Hello", 2 to "World", 3 to "HelloNow", 4 to "WorldNow"), flatList)
                                        }
                                        it("reorder") {
                                            val list = listOf(mapOf(1 to "1a", 2 to "2a", 3 to "3a"),
                                                              mapOf(1 to "1b", 2 to "2b", 3 to "3b"))

                                            val expect = mapOf(1 to listOf("1a", "1b"), 2 to listOf("2a", "2b"), 3 to listOf("3a", "3b"))
                                            assertEquals(expect, list.reorder())
                                        }
                                    }

                                }
                            })