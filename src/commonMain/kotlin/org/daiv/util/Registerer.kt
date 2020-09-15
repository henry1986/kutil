package org.daiv.util

interface Registerer<T : Any> {
    fun register(t: T): Boolean
    fun registerForwarder(registererForwarder: RegistererForwarder<T>) {}
    fun unregisterForwarder(registererForwarder: RegistererForwarder<T>) {}

    fun unregisterAll()

    fun unregister(t: T)
}

interface RegistererForwarder<T : Any> {
    fun eventForward(func: T.() -> Unit)
    suspend fun suspendEventForward(func: suspend T.() -> Unit)
}

interface RegistererHolder<T : Any>  {
    val registerer: Registerer<T>
}

class DefaultRegisterer<T : Any>(private val list: MutableList<T> = mutableListOf()) : Registerer<T>,
                                                                                       RegistererHolder<T>,
                                                                                       Iterable<T> by list {
    private val registererForwarders = mutableListOf<RegistererForwarder<T>>()

    override val registerer: Registerer<T>
        get() = this

    override fun register(t: T) = list.add(t)

    override fun registerForwarder(registererForwarder: RegistererForwarder<T>) {
        registererForwarders.add(registererForwarder)
    }

    override fun unregisterForwarder(registererForwarder: RegistererForwarder<T>) {
        registererForwarders.remove(registererForwarder)
    }

    override fun unregister(t: T) {
        list.remove(t)
    }

    override fun unregisterAll() {
        list.clear()
    }

    suspend fun suspendEvent(func: suspend T.() -> Unit) {
        registererForwarders.forEach { it.suspendEventForward(func) }
        list.forEach { it.func() }
    }

    fun event(func: T.() -> Unit) {
        registererForwarders.forEach { it.eventForward(func) }
        list.forEach(func)
    }
}

class RegistererForwarderImpl<T : Any>(var currentReceiver: T? = null) : RegistererForwarder<T> {

    override fun eventForward(func: T.() -> Unit) {
        currentReceiver?.func()
    }

    override suspend fun suspendEventForward(func: suspend T.() -> Unit) {
        currentReceiver?.func()
    }
}
