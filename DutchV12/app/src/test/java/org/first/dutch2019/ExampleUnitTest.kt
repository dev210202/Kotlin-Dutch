package org.first.dutch2019

import android.util.Log
import com.dutch2019.ui.middle.MiddleLocationViewModel
import com.skt.Tmap.TMapPoint

import org.junit.Assert.*
import org.junit.Test


class ExampleUnitTest {

    var viewModel = MiddleLocationViewModel()

    @Test
    fun changePointTest() {
        var point1 = TMapPoint(0.0, 0.0)
        var point2 = TMapPoint(1.0,-1.0)
        var expectPoint = TMapPoint(0.0, 0.0)
        var changePoint = viewModel.setChangePoint(point1, point2, 1)
        assertEquals(expectPoint, changePoint)
    }
}
