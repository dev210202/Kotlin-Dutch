package org.first.dutchv11

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skt.Tmap.TMapTapi
import kotlinx.android.synthetic.main.location_list_item.view.*
import org.first.dutchv11.Adapter.RecyclerAdapter
import org.first.dutchv11.Data.LocationData
import org.first.dutchv11.databinding.ActivitySearchLocationBinding


class SearchLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchLocationBinding
    private lateinit var viewModel: SearchLocationViewModel
    var REQUEST_OK = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)
        val tmapAPI = TMapTapi(this)
        tmapAPI.setSKTMapAuthentication("l7xx75e02f3eccaa4f56b3f269cb4a9f2b43")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_location)
        viewModel = ViewModelProviders.of(this).get(SearchLocationViewModel::class.java)
        binding.viewmodel = viewModel


        var layoutmanager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutmanager
        binding.recyclerview.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                setTouchEvent(rv, e)
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }

        })
        binding.inputedittext.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {

                viewModel.input = binding.inputedittext.text.toString()
                viewModel.searchLocationData()
                true
            } else {
                false
            }

        }
        binding.searchButton.setOnClickListener {

            viewModel.input = binding.inputedittext.text.toString()
            viewModel.searchLocationData()

        }
        val isDataLoadFailObserver: Observer<Boolean> =
            Observer { isFail ->
                if (isFail) {
                    Log.e("Toast", "!")
                    Toast.makeText(this, "검색결과가 없습니다.", Toast.LENGTH_LONG).show()
                }
            }
        viewModel.isDataLoadFail.observe(this, isDataLoadFailObserver)

        val locationLiveDataObserver: Observer<ArrayList<LocationData>> =
            Observer {
                val newAdapter = RecyclerAdapter(this, viewModel.locationList)
                binding.recyclerview.adapter = newAdapter
            }
        viewModel.locationList.observe(this, locationLiveDataObserver)
    }


    fun setTouchEvent(rv: RecyclerView, e: MotionEvent) {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null) {
            val position = rv.getChildAdapterPosition(child)
            val itemView = rv.layoutManager!!.findViewByPosition(position)
            itemView?.setOnClickListener {
                val intent =
                    Intent(this@SearchLocationActivity, LocationCheckActivity::class.java)
                intent.putExtra(
                    "locationName",
                    itemView.locationnametextview.text.toString()
                )
                intent.putExtra(
                    "locationAddress",
                    itemView.locationaddresstextview.text.toString()
                )
                intent.putExtra(
                    "latitude",
                    viewModel.locationList.value!![position].latitude
                )
                intent.putExtra(
                    "longitude",
                    viewModel.locationList.value!![position].longitude
                )

                startActivityForResult(intent, REQUEST_OK)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OK) {
            Log.e("SearchL REQUEST OK", "1")
            if (resultCode == Activity.RESULT_OK) {
                Log.e("SearchL A REQUEST OK", "1")
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
    }
}



