package org.daiv.util.json

import kotlin.test.Test
import kotlin.test.assertEquals

class JSONHeaderTest{
    @Test
    fun parse() {
        val json = "{i :\"uanr\"}"
        val jsonHeader = JSONHeader.parse("[JSONData, $json]")
        assertEquals("JSONData", jsonHeader.header)
        assertEquals(json, jsonHeader.json)
    }
}
