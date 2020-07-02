package org.first.dutchv11

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.first.dutchv11.Data.LocationSetData
import org.first.dutchv11.databinding.ActivityMainBinding
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var dynamicCreateChooseNum = 0
    var dynamicButtonArray = ArrayList<Int>()
    var Button1_REQUEST_OK = 20
    var Button2_REQUEST_OK = 21

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAppKeyHash()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.serachlocationbutton1.setOnClickListener {
            var intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivityForResult(intent, Button1_REQUEST_OK)
        }
        binding.serachlocationbutton2.setOnClickListener {
            var intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivityForResult(intent, Button2_REQUEST_OK)
        }
        binding.plusbutton.setOnClickListener {

            dynamicButtonCreate()

        }
        binding.searchmiddlelocationbutton.setOnClickListener {
            checkLocationSetting()
        }
    }

    private fun getAppKeyHash() {
        try {
            val info =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: Exception) { // TODO Auto-generated catch block
            Log.e("name not found", e.toString())
        }
    }

    private fun changeDP(value: Int): Int {
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }

    private fun dynamicButtonCreate() {

        dynamicButtonArray.add(dynamicCreateChooseNum, dynamicCreateChooseNum)

        val buttonText = ("위치를 입력해주세요")
        val dynamicButton = Button(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(changeDP(10), changeDP(20), changeDP(10), 0)
        dynamicButton.layoutParams = layoutParams
        dynamicButton.setText(buttonText)
        dynamicButton.setTextColor(Color.BLACK)
        dynamicButton.setBackgroundResource(R.drawable.button_main_location_search)
        dynamicButton.id = dynamicCreateChooseNum
        dynamicCreateChooseNum++
        dynamicButton.setOnClickListener {
            var intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivityForResult(intent, dynamicButtonArray.get(dynamicCreateChooseNum - 1))

        }
        binding.buttonview.addView(dynamicButton)
    }

    private fun checkLocationSetting() {
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var userChooseLocation = data!!.getStringExtra("UserChooseLocationName")
            Log.e("mainGET", userChooseLocation)
            if (requestCode == Button1_REQUEST_OK) {

                binding.serachlocationbutton1.text = userChooseLocation

            } else if (requestCode == Button2_REQUEST_OK) {
                binding.serachlocationbutton2.text = userChooseLocation
            } else {
                for(i in 0..dynamicButtonArray.size - 1){
                    if(requestCode == dynamicButtonArray.get(i)){
                        try{
                            binding.buttonview.findViewById<Button>(i).text = userChooseLocation
                        }catch (e : java.lang.Exception){
                            Toast.makeText(this, "오류입니다. 개발자 이메일: helios2sic@gmail.com 으로 오류내용을 전달해주세요!", Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                    }
                    else{

                    }
                }
            }
        }
    }
}
