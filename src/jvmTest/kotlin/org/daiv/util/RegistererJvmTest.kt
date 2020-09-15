package org.daiv.util

import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class RegistererJvmTest {
    @Test
    fun listenerExecution() {
        val test = TestRegisterer()
        val testListener = mockk<TestListener>(relaxed = true)
        test.register(testListener)
        test.itsHappening()
        verify { testListener.event() }
    }
}