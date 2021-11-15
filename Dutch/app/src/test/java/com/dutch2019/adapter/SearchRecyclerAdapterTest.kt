package com.dutch2019.adapter

import com.dutch2019.util.filtNull
import org.junit.Assert.*
import org.junit.Test

class SearchRecyclerAdapterTest {

    @Test
    fun `null 포함 주소`() {
        val filtValue = filtNull("세종시 null 아름동 null")
        assertEquals("세종시 아름동", filtValue)
    }

    @Test
    fun `null 포함하지 않는 주소`() {
        val filtValue = filtNull("서울시 광진구 세종대학교")
        assertEquals("서울시 광진구 세종대학교", filtValue)
    }
}