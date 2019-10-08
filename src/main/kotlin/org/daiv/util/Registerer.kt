package org.daiv.util

interface Registerer<T : Any> {
    fun register(t: T): Boolean

    fun unregisterAll()

    fun unregister(t: T)
}

class DefaultRegisterer<T : Any>(private val list: MutableList<T> = mutableListOf()) : Registerer<T>,
                                                                                       Iterable<T> by list {
    override fun register(t: T) = list.add(t)

    override fun unregister(t: T) {
        list.remove(t)
    }

    override fun unregisterAll() {
        list.clear()
    }

    fun event(func: T.() -> Unit) {
        list.forEach(func)
    }
}
