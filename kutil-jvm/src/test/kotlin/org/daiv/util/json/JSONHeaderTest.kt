package org.daiv.util.json

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

class JSONHeaderTest :
    Spek({
             describe("jsonHeader") {
                 on("parsing") {
                     it("parse") {
                         val json = "{i :\"uanr\"}"
                         val jsonHeader = JSONHeader.parse("[JSONData, $json]")
                         assertEquals("JSONData", jsonHeader.header)
                         assertEquals(json, jsonHeader.json)
                     }
                 }
             }
         })