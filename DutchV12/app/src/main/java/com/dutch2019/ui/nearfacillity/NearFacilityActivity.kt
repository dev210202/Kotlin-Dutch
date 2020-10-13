package com.dutch2019.ui.nearfacillity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skt.Tmap.TMapPoint
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.data.LocationData
import com.dutch2019.R
import com.dutch2019.databinding.ActivityNearFacilityBinding

class NearFacilityActivity : AppCompatActivity() {

    lateinit var binding: ActivityNearFacilityBinding
    lateinit var viewModel: NearFacilityViewModel
    lateinit var centerPoint : TMapPoint
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_facility)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_near_facility
        )
        viewModel = ViewModelProviders.of(this).get(NearFacilityViewModel::class.java)
        centerPoint = TMapPoint(intent.getDoubleExtra("centerLat",0.0),intent.getDoubleExtra("centerLon",0.0))
        var layoutManager =  LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager

        binding.transbutton.setOnClickListener {
            buttonSelect("trans")
            viewModel.searchNearTransport(centerPoint)
        }
        binding.culturebutton.setOnClickListener {
            buttonSelect("culture")
            viewModel.searchNearCulture(centerPoint)
        }
        binding.foodbutton.setOnClickListener {
            buttonSelect("food")
            viewModel.searchNearFood(centerPoint)
        }
        binding.cafebutton.setOnClickListener {
            buttonSelect("cafe")
            viewModel.searchNearCafe(centerPoint)
        }
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

        val errorObserver : Observer<String> =
            Observer{
                if(Build.VERSION.SDK_INT <= 23){
                    Toast.makeText(this, "오류발생! 카카오톡이 깔려있는지 확인해주세요!", Toast.LENGTH_LONG).show()

                }
                else{
                    Toast.makeText(this, "오류발생 helios2sic@gmail.com으로 발생한 오류를 보내주세요! 앱 개선에 도움이됩니다." + "\n" + viewModel.errorMessage.value, Toast.LENGTH_LONG).show()
                }

            }
        viewModel.errorMessage.observe(this,errorObserver)
        val nearRecyclerObserver: Observer<ArrayList<LocationData>> =
            Observer {
                val newAdapter =
                    SearchRecyclerAdapter(viewModel.locationList)
                binding.recyclerview.adapter = newAdapter
            }
        viewModel.locationList.observe(this, nearRecyclerObserver)
    }

    fun buttonSelect(name: String) {
        when (name) {
            "trans" -> {
                binding.transbutton.isSelected = true
                binding.culturebutton.isSelected = false
                binding.foodbutton.isSelected = false
                binding.cafebutton.isSelected = false
            }
            "culture" -> {
                binding.transbutton.isSelected = false
                binding.culturebutton.isSelected = true
                binding.foodbutton.isSelected = false
                binding.cafebutton.isSelected = false
            }
            "food" -> {
                binding.transbutton.isSelected = false
                binding.culturebutton.isSelected = false
                binding.foodbutton.isSelected = true
                binding.cafebutton.isSelected = false
            }
            "cafe" -> {
                binding.transbutton.isSelected = false
                binding.culturebutton.isSelected = false
                binding.foodbutton.isSelected = false
                binding.cafebutton.isSelected = true
            }
        }
    }

    fun setTouchEvent(rv: RecyclerView, e: MotionEvent) {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null) {
            val position = rv.getChildAdapterPosition(child)
            val itemView = rv.layoutManager!!.findViewByPosition(position)
            itemView?.setOnClickListener {
                var location = viewModel.locationList.value!!.get(position)
                var name = itemView.findViewById<TextView>(R.id.locationnametextview).text.toString()
                var address = itemView.findViewById<TextView>(R.id.locationaddresstextview).text.toString()
                try{
                    viewModel.shareLocation(name, address, location, this)
                }catch (e : Exception){
                    Toast.makeText(this, "카카오톡 공유기능을 사용하실 수 없습니다. 카카오톡을 다운로드 받으셨는지 확인해주세요.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}