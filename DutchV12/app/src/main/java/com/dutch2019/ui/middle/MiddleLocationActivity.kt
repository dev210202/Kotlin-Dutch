package com.dutch2019.ui.middle

import android.content.Context
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
import com.dutch2019.ui.setting.SettingActivity
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import java.lang.Exception

class MiddleLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMiddleLocationBinding
    private lateinit var viewModel: MiddleLocationViewModel
    private lateinit var tMapView: TMapView
    val SETTING_OK = 22
    val RESET_OK = 23
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
            intent.putExtra("centerLat", viewModel.searchNearFacilityPoint.latitude) // Point 수정필요
            intent.putExtra("centerLon", viewModel.searchNearFacilityPoint.longitude) // Point 수정필요
            startActivity(intent)
        }
        binding.settingButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivityForResult(intent, 1)
        }
        object : Thread() {
            override fun run() {
                try {
                    viewModel.calculateCenterPoint()
                    viewModel.middleLocationAddress.postValue(viewModel.getLocationAddress(viewModel.centerPoint))
                    viewModel.nearStationName.postValue(viewModel.findNearSubway(viewModel.centerPoint))
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
                var APoint = TMapPoint(
                    data.getDoubleExtra("APointLat", 0.0),
                    data.getDoubleExtra("APointLon", 0.0)
                )
                var BPoint = TMapPoint(
                    data.getDoubleExtra("BPointLat", 0.0),
                    data.getDoubleExtra("BPointLon", 0.0)
                )
                var ratio = data.getIntExtra("progressValue", 0)

                viewModel.setChangePoint(APoint, BPoint, ratio)
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

    fun setRatioText(textView: TextView) {
        object : Thread() {
            override fun run() {
                try {
                    viewModel.middleLocationAddress.postValue(
                        viewModel.getLocationAddress(
                            viewModel.changePoint!!
                        )
                    )
                    viewModel.searchNearFacilityPoint = viewModel.changePoint!!
                    viewModel.nearStationName.postValue(viewModel.findNearSubway(viewModel.changePoint!!))
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

    fun setBallonOverlayClickEvent(tMapView: TMapView, textView: TextView) {
        tMapView.setOnMarkerClickEvent(object : TMapView.OnCalloutMarker2ClickCallback {
            override fun onCalloutMarker2ClickEvent(p0: String?, p1: TMapMarkerItem2) {
                object : Thread() {
                    override fun run() {
                        try {
                            if (p1.id == "ratiomarkerItem") {
                                var point = p1.tMapPoint
                                viewModel.middleLocationAddress.postValue(
                                     viewModel.getLocationAddress(
                                            point
                                    )
                                )
                                viewModel.searchNearFacilityPoint = point
                                viewModel.nearStationName.postValue(viewModel.findNearSubway(point))
                                textView.setTextColor(
                                    ContextCompat.getColor(
                                        baseContext,
                                        R.color.blue
                                    )
                                )
                                textView.text = "비율변경지점 결과"
                            } else if (p1.id == "middlemarkerItem") {
                                var point = p1.tMapPoint
                                viewModel.middleLocationAddress.postValue(
                                    viewModel.getLocationAddress(
                                        point
                                    )
                                )
                                viewModel.searchNearFacilityPoint = point
                                viewModel.nearStationName.postValue(viewModel.findNearSubway(point))
                                textView.setTextColor(
                                    ContextCompat.getColor(
                                        baseContext,
                                        R.color.orange
                                    )
                                )
                                textView.text = "중간지점 결과"
                            } else {
                                var point = p1.tMapPoint
                                viewModel.middleLocationAddress.postValue(
                                    viewModel.getLocationAddress(
                                        point
                                    )
                                )
                                viewModel.searchNearFacilityPoint = point
                                viewModel.nearStationName.postValue(viewModel.findNearSubway(point))
                                textView.setTextColor(
                                    ContextCompat.getColor(
                                        baseContext,
                                        R.color.black
                                    )
                                )
                                textView.text = "검색지점 결과"
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }.start()
            }

        })
    }

}

