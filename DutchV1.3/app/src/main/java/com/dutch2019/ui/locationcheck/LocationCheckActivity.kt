package com.dutch2019.ui.locationcheck

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.dutch2019.model.LocationData
import com.dutch2019.R
import com.dutch2019.databinding.ActivityLocationCheckBinding
import com.dutch2019.db.AppDB
import com.dutch2019.model.LocationInfo
import com.dutch2019.repository.LocationRepository

class LocationCheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationCheckBinding
    private lateinit var locationInfo: LocationInfo
    private lateinit var tMapView: TMapView
    private var locationRepository = LocationRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_check)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_location_check
        )
        locationInfo = LocationInfo(
            0,
            intent.getStringExtra("locationName"),
            intent.getStringExtra("locationAddress"),
            intent.getDoubleExtra("latitude", 0.0),
            intent.getDoubleExtra("longitude", 0.0)
        )
        Log.e("LocationInfo", locationInfo.toString())
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("l7xx75e02f3eccaa4f56b3f269cb4a9f2b43")
        binding.mapview.addView(tMapView)

        infoSetting()

        binding.locationsetbutton.setOnClickListener {
            var thread = Thread(Runnable {

                locationRepository.setDB(application)
                locationRepository.deleteAll()
                locationRepository.insertData(locationInfo)

            }).start()
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    private fun infoSetting() {
        val markerItemPoint = TMapPoint(locationInfo.latitude, locationInfo.longitude)
        val markerImage = BitmapFactory.decodeResource(
            this.resources,
            R.drawable.result_ic_marker_black
        )
        val markerItem = TMapMarkerItem()
        markerItem.icon = markerImage
        markerItem.tMapPoint = markerItemPoint
        markerItem.setPosition(0.5F, 1F)
        tMapView.setCenterPoint(locationInfo.longitude, locationInfo.latitude)
        tMapView.addMarkerItem("markerItem", markerItem)

        binding.nametextview.text = locationInfo.name
        binding.addresstextview.text = locationInfo.adress
    }
}