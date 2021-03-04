package org.daiv.time

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

//fun Date.isoTime(): String {
//    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//    return sdf.format(this)
//}
//
//fun Date.isoTimeWithMillis(): String {
//    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z")
//    return sdf.format(this)
//}

/**
 * returns iso Date time
 */
fun Long.isoTimeOffsetWithSystemTimeZone(zoneId: ZoneId = ZoneOffset.systemDefault()): String {
    return DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(Locale.US).withZone(zoneId)
        .format(Instant.ofEpochMilli(this))
}

actual fun Long.isoTime() = isoTimeOffsetWithSystemTimeZone()

/**
 * returns epochMilli
 */
actual fun String.isoTime(): Long {
    return Instant.from(OffsetDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)).toEpochMilli()
}
