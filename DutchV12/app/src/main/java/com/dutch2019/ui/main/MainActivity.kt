package com.dutch2019.ui.main

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dutch2019.*
import com.dutch2019.data.LocationData
import com.dutch2019.data.LocationSetData
import com.dutch2019.databinding.ActivityMainBinding
import com.dutch2019.ui.middle.MiddleLocationActivity
import com.dutch2019.ui.search.SearchLocationActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: MainViewModel
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LocationSetData.data.clear()
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        dialogShow()

        binding.logo.setOnClickListener {
            val intent = Intent(this, DeveloperInfoActivity::class.java)
            startActivity(intent)
        }

        binding.searchmiddlelocationbutton.setOnClickListener {
            if (LocationSetData.data.size > 1) {
                val intent = Intent(this, MiddleLocationActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "2개 이상 위치를 설정하고 중간지점을 검색해주세요!", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun changeIntToDP(value: Int): Int {

        val displayMetrics = resources.displayMetrics
        return (value * displayMetrics.density).roundToInt()

    }

    fun dynamicButtonCreate(view: View) {

        viewmodel.dynamicButtonArray.add(
            viewmodel.dynamicCreateChooseNum,
            viewmodel.dynamicCreateChooseNum
        )

        val buttonText = ("위치를 입력해주세요")
        val dynamicButton = Button(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, changeIntToDP(52)
        )
        layoutParams.setMargins(changeIntToDP(16), changeIntToDP(20), changeIntToDP(16), 0)
        dynamicButton.layoutParams = layoutParams
        dynamicButton.text = buttonText
        dynamicButton.textSize = 16F
        dynamicButton.setTextColor(Color.rgb(134, 134, 134))
        dynamicButton.setBackgroundResource(R.drawable.button_main_location_search)
        dynamicButton.id = viewmodel.dynamicCreateChooseNum

        viewmodel.dynamicCreateChooseNum++
        dynamicButton.setOnClickListener {
            val intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivityForResult(
                intent,
                viewmodel.dynamicButtonArray[dynamicButton.id]
            )
        }

        binding.buttonview.addView(dynamicButton)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            val userChooseLocation = LocationData(
                data!!.getStringExtra("UserChooseLocationName"),
                data.getStringExtra("UserChooseLocationAddress"),
                data.getDoubleExtra("UserChooseLocationLatitude", 0.0),
                data.getDoubleExtra("UserChooseLocationLongitude", 0.0)
            )
            for (i in 0..viewmodel.dynamicButtonArray.lastIndex) {
                if (requestCode == viewmodel.dynamicButtonArray[i]) {
                    setLocationName(i, userChooseLocation)

                    binding.buttonview.findViewById<Button>(i).setTextColor(Color.rgb(47, 47, 47))
                    binding.buttonview.findViewById<Button>(i).text =
                        userChooseLocation.locationName
                }
            }
        }
    }

    private fun setLocationName(i: Int, userChooseLocation: LocationData) {
        if (binding.buttonview.findViewById<Button>(i).text != "위치를 입력해주세요") {
            for (j in 0 until LocationSetData.data.size) {
                if (binding.buttonview.findViewById<Button>(j) != null) {
                    val removeData =
                        binding.buttonview.findViewById<Button>(i).text.toString()
                    if (LocationSetData.data[j].locationName == removeData) {

                        Log.e("LocationSetData Before", LocationSetData.data.toString())
                        LocationSetData.data.removeAt(j)
                        LocationSetData.data.add(j, userChooseLocation)
                        Log.e("LocationSetData After", LocationSetData.data.toString())
                    }
                }

            }
        } else {
            LocationSetData.data.add(userChooseLocation)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationSetData.data.clear()
    }

    fun dialogShow() {
        var time = Calendar.getInstance()
        var a = time.time
        var format1 = SimpleDateFormat("yyyyMMdd")

        if (format1.format(a).toInt() in 20201020..20201101) {

            var sharedPreferences = getSharedPreferences("dontLook", Context.MODE_PRIVATE)
            if (sharedPreferences.getBoolean("boolean", false)) {

            } else {
                dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.custom_dialog)
                dialog.show();

            }
        }


    }

    fun dontLookButtonClick(view: View) {

        var sharedPreferences = getSharedPreferences("dontLook", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putBoolean("boolean", true)
        editor.commit()
        dialog.dismiss()
    }

    fun closeButtonClick(view: View) {
        dialog.dismiss()
    }



}