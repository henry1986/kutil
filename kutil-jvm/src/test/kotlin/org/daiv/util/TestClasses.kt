package org.daiv.util

class TestListener {
    fun event() {}
}

class TestRegisterer(private val registerer: DefaultRegisterer<TestListener> = DefaultRegisterer()) :
    Registerer<TestListener> by registerer {
    fun itsHappening() {
        registerer.forEach(TestListener::event)
    }
}
