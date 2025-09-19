package com.example.collegeerp

import org.junit.Test
import org.junit.Assert.*

class SimpleTest {
    
    @Test
    fun `addition is correct`() {
        assertEquals(4, 2 + 2)
    }
    
    @Test
    fun `string concatenation works`() {
        val result = "Hello" + " " + "World"
        assertEquals("Hello World", result)
    }
}