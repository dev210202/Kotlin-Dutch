package com.dutch2019.ui.middle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dutch2019.R
import com.dutch2019.databinding.ActivityMiddleLocationBinding
import com.dutch2019.ui.nearfacillity.NearFacilityActivity
import com.dutch2019.ui.ratio.RatioActivity
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import java.lang.Exception

class MiddleLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMiddleLocationBinding
    private lateinit var viewModel: MiddleLocationViewModel
    private lateinit var tMapView: TMapView
    private val SETTING_OK = 22
    private val RESET_OK = 23
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middle_location)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_middle_location
        )
        viewModel = ViewModelProviders.of(this).get(MiddleLocationViewModel::class.java)

        tMapView = TMapView(this)
        binding.middlemapview.addView(tMapView)
        setBallonOverlayClickEvent(tMapView, binding.middlelocationresultTextview)

        binding.nearfacilitybutton.setOnClickListener {
            val intent = Intent(this, NearFacilityActivity::class.java)
            Log.e("centerLat", "" + viewModel.searchNearFacilityPoint.latitude)
            Log.e("centerLon", "" + viewModel.searchNearFacilityPoint.longitude)
            intent.putExtra("centerLat", viewModel.searchNearFacilityPoint.latitude) // Point 수정필요
            intent.putExtra("centerLon", viewModel.searchNearFacilityPoint.longitude) // Point 수정필요
            startActivity(intent)
        }
        binding.settingButton.setOnClickListener {
            val intent = Intent(this, RatioActivity::class.java)
            startActivityForResult(intent, 1)
        }
        object : Thread() {
            override fun run() {
                try {
                    viewModel.calculateCenterPoint()
                    viewModel.middleLocationAddress.value =
                        viewModel.getLocationAddress(viewModel.centerPoint)
                    viewModel.nearStationName.value =
                        viewModel.findNearSubway(viewModel.centerPoint)
                    viewModel.markSearchLoaction(tMapView, this@MiddleLocationActivity)
                    viewModel.markMiddleLocation(tMapView, this@MiddleLocationActivity)
                    viewModel.setPolyLine(tMapView)
                    viewModel.mapAutoZoom(tMapView)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }.start()

        val nearSubwayObserver: Observer<String> = Observer {
            binding.nearsubwaytextview.text = viewModel.nearStationName.value
        }
        viewModel.nearStationName.observe(this, nearSubwayObserver)
        val middleLocationObserver: Observer<String> =
            Observer {
                binding.middleaddresstextview.text = viewModel.middleLocationAddress.value
            }
        viewModel.middleLocationAddress.observe(this, middleLocationObserver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == SETTING_OK) {
            Log.e("SETTING OK!!", "!")
            if (data != null) {
                val pointA = TMapPoint(
                    data.getDoubleExtra("pointALat", 0.0),
                    data.getDoubleExtra("pointALon", 0.0)
                )
                val pointB = TMapPoint(
                    data.getDoubleExtra("pointBLat", 0.0),
                    data.getDoubleExtra("pointBLon", 0.0)
                )
                val ratio = data.getIntExtra("progressValue", 0)

                viewModel.setChangePoint(pointA, pointB, ratio)
                viewModel.setMarkRatioLocation(viewModel.changePoint!!, tMapView, this)

                setRatioText(binding.middlelocationresultTextview)

            }
        } else if (resultCode == RESET_OK) {
            binding.middlelocationresultTextview.text = "중간지점 결과"
            binding.middlelocationresultTextview.setTextColor(
                ContextCompat.getColor(
                    baseContext,
                    R.color.orange
                )
            )
            viewModel.resetChangePoint(tMapView)
            binding.middleaddresstextview.text = viewModel.middleLocationAddress.value
            binding.nearsubwaytextview.text = viewModel.nearStationName.value
        }
    }

    private fun setRatioText(textView: TextView) {
        object : Thread() {
            override fun run() {
                try {
                    viewModel.middleLocationAddress.value = viewModel.getLocationAddress(viewModel.changePoint!!)

                    viewModel.searchNearFacilityPoint = viewModel.changePoint!!
                    viewModel.nearStationName.value =
                        viewModel.findNearSubway(viewModel.changePoint!!)

                    textView.setTextColor(
                        ContextCompat.getColor(
                            baseContext,
                            R.color.blue
                        )
                    )
                    textView.text = "비율변경지점 결과"
                    Log.e("changePoint", viewModel.changePoint.toString())
                    Log.e("centerPoint", viewModel.centerPoint.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()

    }

    private fun setBallonOverlayClickEvent(tMapView: TMapView, textView: TextView) {
        tMapView.setOnMarkerClickEvent { _, p1 ->
            object : Thread() {
                override fun run() {
                    val point = p1.tMapPoint
                    try {
                        when (p1.id) {
                            "ratiomarkerItem" -> {
                                viewModel.middleLocationAddress.value =
                                    viewModel.getLocationAddress(point)
                                viewModel.searchNearFacilityPoint = point
                                viewModel.nearStationName.value = viewModel.findNearSubway(point)
                                textView.setTextColor(
                                    ContextCompat.getColor(
                                        baseContext,
                                        R.color.blue
                                    )
                                )
                                textView.text = "비율변경지점 결과"
                            }
                            "middlemarkerItem" -> {
                                viewModel.middleLocationAddress.value =
                                    viewModel.getLocationAddress(point)

                                viewModel.searchNearFacilityPoint = point
                                viewModel.nearStationName.value = viewModel.findNearSubway(point)
                                textView.setTextColor(
                                    ContextCompat.getColor(
                                        baseContext,
                                        R.color.orange
                                    )
                                )
                                textView.text = "중간지점 결과"
                            }
                            else -> {
                                viewModel.middleLocationAddress.value =
                                    viewModel.getLocationAddress(point)

                                viewModel.searchNearFacilityPoint = point
                                viewModel.nearStationName.value = viewModel.findNearSubway(point)
                                textView.setTextColor(
                                    ContextCompat.getColor(
                                        baseContext,
                                        R.color.black
                                    )
                                )
                                textView.text = "검색지점 결과"
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }.start()
        }
    }

}

