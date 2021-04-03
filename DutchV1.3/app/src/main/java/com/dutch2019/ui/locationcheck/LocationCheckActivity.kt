package com.dutch2019.ui.locationcheck

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.dutch2019.model.LocationData
import com.dutch2019.R
import com.dutch2019.base.BaseActivity
import com.dutch2019.databinding.ActivityLocationCheckBinding
import com.dutch2019.model.LocationInfo
import com.dutch2019.repository.LocationRepository

class LocationCheckActivity : BaseActivity<ActivityLocationCheckBinding, LocationCheckViewModel>(
    R.layout.activity_location_check,
    LocationCheckViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        var locationData = (intent.getSerializableExtra("LocationData") as LocationData).let { data ->
//            viewModel.setData(data)
//        }


//        binding.locationsetbutton.setOnClickListener {
//            var thread = Thread(Runnable {
//
//                locationRepository.setDB(application)
//                locationRepository.deleteAll()
//                locationRepository.insertData(locationInfo)
//
//            }).start()
//            val intent = Intent()
//            setResult(Activity.RESULT_OK, intent)
//            finish()
//        }

    }


}