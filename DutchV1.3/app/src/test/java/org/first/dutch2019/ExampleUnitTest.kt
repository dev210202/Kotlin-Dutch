package org.first.dutch2019

import com.dutch2019.ui.middle.MiddleLocationViewModel
import com.dutch2019.ui.search.SearchLocationViewModel
import com.skt.Tmap.TMapPOIItem

import org.junit.Assert.*
import org.junit.Test


class ExampleUnitTest {
    var viewModel = MiddleLocationViewModel()

    @Test
    fun test() {
        viewModel.getMiddleRouteTime()
    }

    fun addressNameCheck(address: String, item: TMapPOIItem): String {
        var add = address

        if (item.upperAddrName != null) {
           add =  add.plus(item.upperAddrName)
        }

        return add
    }

}
