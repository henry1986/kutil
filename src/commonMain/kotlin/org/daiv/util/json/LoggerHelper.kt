package org.daiv.util.json

import mu.KLogger


fun <T> Iterable<T>.log(logger: KLogger, name: String = "", block: (Int, T) -> Unit = { i, e -> logger.trace { "$i: $e" } }) {
    logger.trace { "log $name" }
    forEachIndexed(block)
}