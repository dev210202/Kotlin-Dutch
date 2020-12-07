package org.first.dutch2019

import com.dutch2019.ui.search.SearchLocationViewModel
import com.skt.Tmap.TMapPOIItem

import org.junit.Assert.*
import org.junit.Test


class ExampleUnitTest {
    var viewModel = SearchLocationViewModel()

    @Test
    fun test() {
        var address = "123"
        var item = TMapPOIItem()
        item.upperAddrName = "1"
        item.middleAddrName = "2"
        item.lowerAddrName = "3"
        address = addressNameCheck(address, item)
        assertEquals(address, "1234")
    }

    fun addressNameCheck(address: String, item: TMapPOIItem): String {
        var add = address

        if (item.upperAddrName != null) {
           add =  add.plus(item.upperAddrName)
        }

        return add
    }

}
