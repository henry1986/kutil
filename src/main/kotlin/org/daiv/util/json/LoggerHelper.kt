package org.daiv.util.json

import mu.KLogger

fun <T> Iterable<T>.log(logger: KLogger, name: String = "") {
    logger.trace { "log $name" }
    forEachIndexed { i, e ->
        logger.trace { "$i: $e" }
    }
}