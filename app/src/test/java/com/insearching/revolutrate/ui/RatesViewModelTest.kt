package com.insearching.revolutrate.ui

import com.insearching.revolutrate.util.AutoInjectKoinTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RatesViewModelTest : AutoInjectKoinTest() {
    
    @Test
    fun testFailed() {
        assertEquals(4, 3)
    }
    
    @Test
    fun testSuccess() {
        assertEquals(4, 2 + 2)
    }
}