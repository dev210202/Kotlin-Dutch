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
import androidx.lifecycle.ViewModelProviders
import org.first.dutchv11.databinding.ActivityMainBinding
import java.security.MessageDigest
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel : MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getAppKeyHash()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)



        binding.searchlocationbutton1.setOnClickListener {
            val intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivityForResult(intent, viewmodel.Button1_REQUEST_OK)
        }
        binding.searchlocationbutton2.setOnClickListener {
            val intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivityForResult(intent, viewmodel.Button2_REQUEST_OK)
        }
        binding.plusbutton.setOnClickListener {
                dynamicButtonCreate()
        }
        binding.searchmiddlelocationbutton.setOnClickListener {
            if(isLocationSet()){
                val intent = Intent(this, MiddleLocationActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "위치를 2개 이상 설정하고 중간지점 찾기를 눌러주세요!",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getAppKeyHash() {
        try {
            val info =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: Exception) {
            Log.e("name not found", e.toString())
        }
    }

    private fun changeIntToDP(value: Int): Int {

        val displayMetrics = resources.displayMetrics
        return (value * displayMetrics.density).roundToInt()
    }

    private fun dynamicButtonCreate() {

        viewmodel.dynamicButtonArray.add(viewmodel.dynamicCreateChooseNum, viewmodel.dynamicCreateChooseNum)

        val buttonText = ("위치를 입력해주세요")
        val dynamicButton = Button(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(changeIntToDP(10), changeIntToDP(20), changeIntToDP(10), 0)
        dynamicButton.layoutParams = layoutParams
        dynamicButton.text = buttonText
        dynamicButton.setTextColor(Color.BLACK)
        dynamicButton.setBackgroundResource(R.drawable.button_main_location_search)
        dynamicButton.id = viewmodel.dynamicCreateChooseNum
        viewmodel.dynamicCreateChooseNum++
        dynamicButton.setOnClickListener {
            val intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivityForResult(intent,
                viewmodel.dynamicButtonArray[viewmodel.dynamicCreateChooseNum - 1]
            )

        }
        binding.buttonview.addView(dynamicButton)



    }

    private fun isLocationSet() : Boolean{
        return binding.searchlocationbutton1.text != "위치를 입력해주세요" || binding.searchlocationbutton2.text != "위치를 입력해주세요"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            val userChooseLocation = data!!.getStringExtra("UserChooseLocationName")

            when (requestCode) {
                viewmodel.Button1_REQUEST_OK -> {

                    binding.searchlocationbutton1.text = userChooseLocation

                }
                viewmodel.Button2_REQUEST_OK -> {
                    binding.searchlocationbutton2.text = userChooseLocation
                }
                else -> {
                    for(i in 0 until viewmodel.dynamicButtonArray.size){
                        if(requestCode == viewmodel.dynamicButtonArray[i]){
                            try{
                                binding.buttonview.findViewById<Button>(i).text = userChooseLocation
                            }catch (e : java.lang.Exception){
                                Toast.makeText(this, "오류입니다. 개발자 이메일: helios2sic@gmail.com 으로 오류내용을 전달해주세요!", Toast.LENGTH_LONG).show()
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
        }
    }
}
