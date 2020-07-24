package com.dutch2019

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dutch2019.databinding.ActivityMiddleLocationBinding
import com.skt.Tmap.TMapView

class MiddleLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMiddleLocationBinding
    private lateinit var viewModel: MiddleLocationViewModel
    private lateinit var tMapView: TMapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middle_location)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_middle_location)
        viewModel = ViewModelProviders.of(this).get(MiddleLocationViewModel::class.java)

        tMapView = TMapView(this)
        binding.middlemapview.addView(tMapView)

        binding.nearfacilitybutton.setOnClickListener {
            val intent = Intent(this, NearFacilityActivity::class.java)
            intent.putExtra("centerLat", viewModel.centerPoint.latitude)
            intent.putExtra("centerLon", viewModel.centerPoint.longitude)
            startActivity(intent)
        }

        object : Thread() {
            override fun run() {
               viewModel.calculateMiddleLocation()
                viewModel.findNearSubway()
                viewModel.markSearchLoaction(tMapView, this@MiddleLocationActivity)
                viewModel.markMiddleLocation(tMapView, this@MiddleLocationActivity)
                viewModel.setPolyLine(tMapView)
                viewModel.mapAutoZoom(tMapView)
            }
        }.start()

        val nearSubwayObserver: Observer<String> = Observer {
            binding.nearsubwaytextview.text = viewModel.nearStationName.value
        }
        viewModel.nearStationName.observe(this, nearSubwayObserver)
        val middleLocationObserver : Observer<String> =
            Observer {
                binding.middleaddresstextview.text = viewModel.middleLocationAddress.value
            }
        viewModel.middleLocationAddress.observe(this, middleLocationObserver)
    }
}