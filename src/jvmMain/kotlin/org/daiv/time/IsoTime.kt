package org.daiv.time

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.isoTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return sdf.format(this)
}

fun Date.isoTimeWithMillis(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z")
    return sdf.format(this)
}

fun Long.isoTimeOffsetWithSystemTimeZone(): String {
    return DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(Locale.US).withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))
}

fun String.isoTime(): Date {
    return Date.from(Instant.from(OffsetDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)))
}
