package org.daiv.time

expect fun Long.isoTime():String
expect fun Long.isoTimeWithoutMillis():String
expect fun String.isoTime():Long