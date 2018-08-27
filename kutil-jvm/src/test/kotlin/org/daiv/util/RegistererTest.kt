package org.daiv.util

import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class RegistererTest : Spek({
                                describe("Registerer") {
                                    on("test") {
                                        it("listener execution") {

                                            val test = TestRegisterer()
                                            val testListener = mockk<TestListener>(relaxed = true)
                                            test.register(testListener)
                                            test.itsHappening()
                                            verify { testListener.event() }
                                        }
                                    }
                                }
                            })