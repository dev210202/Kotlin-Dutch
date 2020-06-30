package org.first.dutchv11

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skt.Tmap.TMapTapi
import io.reactivex.Observable
import org.first.dutchv11.Adapter.RecyclerAdapter
import org.first.dutchv11.Data.LocationData
import org.first.dutchv11.databinding.ActivitySearchLocationBinding


class SearchLocationActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchLocationBinding
    lateinit var viewModel: SearchLocationViewModel

    var emptyData = mutableListOf<LocationData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)
        var tmapAPI = TMapTapi(this)
        tmapAPI.setSKTMapAuthentication("l7xx75e02f3eccaa4f56b3f269cb4a9f2b43")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_location)
        viewModel = ViewModelProviders.of(this).get(SearchLocationViewModel::class.java)
        binding.viewmodel = viewModel

        var adapter = RecyclerAdapter(this, emptyData)

        var layoutmanager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutmanager
        binding.recyclerview.adapter = adapter

        binding.searchButton.setOnClickListener {

            viewModel.input = binding.inputedittext.text.toString()
            viewModel.searchLocationData()

            while(true){
                if (viewModel.isComplete == viewModel.COMPLETE){ // polling 작업으로 비동기처리
                    adapter = RecyclerAdapter(this, viewModel.locationList)
                    binding.recyclerview.adapter = adapter
                    binding.recyclerview.invalidate()
                    adapter.notifyDataSetChanged()
                    break;
                }
                else if(viewModel.isComplete == viewModel.LOAD_FAIL){
                    Toast.makeText(applicationContext, "검색결과가 없습니다.", Toast.LENGTH_LONG)
                    break;
                }
            }
        }
    }
}



