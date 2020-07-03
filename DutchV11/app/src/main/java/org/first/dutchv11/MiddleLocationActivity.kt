package org.first.dutchv11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.skt.Tmap.TMapView
import org.first.dutchv11.databinding.ActivityMiddleLocationBinding

class MiddleLocationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMiddleLocationBinding
    private lateinit var viewModel: MiddleLocationViewModel
    private lateinit var tMapView : TMapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middle_location)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_middle_location)
        viewModel = ViewModelProviders.of(this).get(MiddleLocationViewModel::class.java)

        tMapView = TMapView(this)
        binding.middlemapview.addView(tMapView)
        viewModel.calculateMiddleLocation()

        // tMapView.setCenterPoint()

        var middleLocationObserver : Observer<String> =
            Observer {
                binding.middleaddresstextview.text = viewModel.middleLocationAddress.value
            }
        viewModel.middleLocationAddress.observe(this, middleLocationObserver)
    }
}