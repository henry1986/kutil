package org.daiv.time

import kotlin.js.Date


actual fun Long.isoTime() = Date(this).toISOString()
actual fun String.isoTime(): Long {
    return Date(this).getTime().toLong()
}