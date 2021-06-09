package org.first.dutch2019

import com.dutch2019.ui.detailinfo.DetailInfoViewModel
import com.dutch2019.ui.middle.MiddleLocationViewModel
import com.dutch2019.ui.search.SearchLocationViewModel
import com.skt.Tmap.TMapPOIItem

import org.junit.Assert.*
import org.junit.Test


class ExampleUnitTest {
    @Test
    fun test(){
        var viewModel = DetailInfoViewModel()
        var result = viewModel.filterText("[영업시간]12:00~14:30 ; 18:00~22:00;[좌석수]130;[주차]주차가능;[휴무]연중무휴;")
        assertEquals("!!", result)
    }
}
