package com.dutch2019.extension

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.dutch2019.adapter.DynamicButtonRecyclerAdapter
import com.dutch2019.adapter.DeleteRecentRecyclerAdapter
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationDataDB
import com.dutch2019.model.LocationInfoList
import com.dutch2019.model.LocationPoint
import com.dutch2019.ui.main.MainFragmentDirections
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.middle.MiddleLocationFragmentDirections
import com.dutch2019.ui.middle.MiddleLocationViewModel
import com.dutch2019.ui.nearfacillity.NearFacilityViewModel
import com.dutch2019.ui.recent.RecentViewModel
import kotlinx.android.synthetic.main.fragment_near_facility.view.*

@BindingAdapter(value = ["setlocationbuttonclick"])
fun setLocationButtonClick(button: Button, viewModel: BaseViewModel) {
    button.setOnClickListener { view ->
        (viewModel as MainViewModel).replaceDynamicButtonData(
            viewModel.checkLocationInfo.value!!.id,
            viewModel.checkLocationInfo.value!!
        )
        val navController = view.findNavController()
        navController.popBackStack()
        navController.popBackStack()
    }
}

@BindingAdapter(value = ["searchmiddlelocationbuttonclick"])
fun searchMiddleLocationButtonClick(button: Button, viewModel: BaseViewModel) {
    val vm = (viewModel as MainViewModel)

    val locationInfoList = LocationInfoList()


    button.setOnClickListener { view ->
        val locationInfoData =
            (button.rootView.recyclerview.adapter as DynamicButtonRecyclerAdapter).getLocationData()
        for (i in 0 until locationInfoData.size) {
            if (locationInfoData[i].name != "위치를 설정해주세요") {
                val locationInfo = locationInfoData[i]
                locationInfoList.add(locationInfo)
            }
        }

        if (locationInfoList.size > 1) {
            val locationDataDB = LocationDataDB()
            locationDataDB.list = locationInfoList
            vm.insertDataInDB(locationDataDB)
            view.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToMiddleLocationFragment(locationInfoList)
            )
        } else {
            Toast.makeText(button.context, "위치를 2개 이상으로 설정해주세요!", Toast.LENGTH_LONG).show()
        }
    }
}

@BindingAdapter(value = ["searchnearfacilitybuttonclick"])
fun searchNearFacilityButtonClick(button: Button, viewModel: BaseViewModel) {
    val vm = (viewModel as MiddleLocationViewModel)
    val locationPoint = LocationPoint(0.0, 0.0)
    locationPoint.latitude = vm.getCenterPoint().latitude
    locationPoint.longitude = vm.getCenterPoint().longitude
    button.setOnClickListener { view ->
        view.findNavController().navigate(
            MiddleLocationFragmentDirections.actionMiddleLocationFragmentToNearFacilityFragment(
                locationPoint
            )
        )
    }
}

@BindingAdapter(value = ["facilitybuttonclick"])
fun facilityButtonClick(button: Button, viewModel: BaseViewModel) {
    val vm = (viewModel as NearFacilityViewModel)
    button.setOnClickListener { view ->
        setButtonSelect(view)
        searchNearFacility(view, vm)
    }
}

@BindingAdapter(value = ["deletecomplete"])
fun deleteComplete(button: Button, viewModel: BaseViewModel) {
    val vm = (viewModel as RecentViewModel)
    var adapter = button.rootView.recyclerview.adapter
    if(button.rootView.recyclerview.adapter == null){
        adapter = DeleteRecentRecyclerAdapter()
    }
    button.setOnClickListener { view ->
        vm.deleteLocationDB((adapter as DeleteRecentRecyclerAdapter).getDeleteList())
        view.findNavController().popBackStack()
    }
}

private fun setButtonSelect(view: View) {
    when (view) {
        view.rootView.transbutton -> {
            view.rootView.transbutton.isSelected = true
            view.rootView.culturebutton.isSelected = false
            view.rootView.foodbutton.isSelected = false
            view.rootView.cafebutton.isSelected = false
        }
        view.rootView.culturebutton -> {
            view.rootView.transbutton.isSelected = false
            view.rootView.culturebutton.isSelected = true
            view.rootView.foodbutton.isSelected = false
            view.rootView.cafebutton.isSelected = false
        }
        view.rootView.foodbutton -> {
            view.rootView.transbutton.isSelected = false
            view.rootView.culturebutton.isSelected = false
            view.rootView.foodbutton.isSelected = true
            view.rootView.cafebutton.isSelected = false
        }
        view.rootView.cafebutton -> {
            view.rootView.transbutton.isSelected = false
            view.rootView.culturebutton.isSelected = false
            view.rootView.foodbutton.isSelected = false
            view.rootView.cafebutton.isSelected = true
        }
    }
}

private fun searchNearFacility(view: View, viewModel: NearFacilityViewModel) {
    var category = ""
    when (view) {
        view.rootView.transbutton -> {
            category = viewModel.setNearFacilityCategory("대중교통")
        }
        view.rootView.culturebutton -> {
            category = viewModel.setNearFacilityCategory("문화시설")
        }
        view.rootView.foodbutton -> {
            category = viewModel.setNearFacilityCategory("음식점")
        }
        view.rootView.cafebutton -> {
            category = viewModel.setNearFacilityCategory("카페")
        }
    }
    viewModel.searchNearFacility(viewModel.locationPoint, category)
}