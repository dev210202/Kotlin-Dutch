package org.first.dutchv11

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.skt.Tmap.TMapTapi
import org.first.dutchv11.Adapter.RecyclerAdapter
import org.first.dutchv11.Data.LocationData
import org.first.dutchv11.databinding.ActivitySearchLocationBinding


class SearchLocationActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchLocationBinding
    lateinit var viewModel: SearchLocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)
        var tmapAPI = TMapTapi(this)
        tmapAPI.setSKTMapAuthentication("l7xx75e02f3eccaa4f56b3f269cb4a9f2b43")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_location)
        viewModel = ViewModelProviders.of(this).get(SearchLocationViewModel::class.java)
        binding.viewmodel = viewModel


        var layoutmanager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutmanager

        binding.searchButton.setOnClickListener {

            viewModel.input = binding.inputedittext.text.toString()
            viewModel.searchLocationData()

        }
        var isDataLoadFailObserver : Observer<Boolean> =
            Observer { isFail ->
                if(isFail){
                    Log.e("Toast","!")
                    Toast.makeText(this, "검색결과가 없습니다.", Toast.LENGTH_LONG).show()
                }
            }
        viewModel.isDataLoadFail.observe(this, isDataLoadFailObserver)

        var locationLiveDataObserver: Observer<ArrayList<LocationData>> =
            Observer { livedata ->
                var newAdapter = RecyclerAdapter(this, viewModel.locationList)
                binding.recyclerview.adapter = newAdapter
            }
        viewModel.locationList.observe(this, locationLiveDataObserver)
    }
}



