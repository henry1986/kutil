package org.daiv.util.json

data class JSONHeader(val header: String, val json: String) {
    fun serialize() = "[$header, $json]"

    companion object {
        fun parse(message: String): JSONHeader {
            val split = message.split(",")
            val first = split[0]
            val json = message.drop(first.length)
                .dropLast(1)
                .drop(1)
                .trim()
            val header = first.trim()
                .drop(1)
            return JSONHeader(header, json)
        }
    }
}