package org.first.dutchv11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.first.dutchv11.databinding.ActivityLocationCheckBinding

class LocationCheckActivity : AppCompatActivity() {

    lateinit var binding : ActivityLocationCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_check)
        binding = DataBindingUtil.setContentView(this,  R.layout.activity_location_check)
        binding.mapview
    }
}