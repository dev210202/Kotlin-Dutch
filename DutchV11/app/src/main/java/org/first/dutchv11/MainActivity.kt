package org.first.dutchv11

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import org.first.dutchv11.Data.LocationData
import org.first.dutchv11.Data.LocationSetData
import org.first.dutchv11.databinding.ActivityMainBinding
import java.security.MessageDigest
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: MainViewModel
    private var isAlreadyLocationExist = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getAppKeyHash()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.plusbutton.setOnClickListener {

            dynamicButtonCreate()

        }
        binding.searchmiddlelocationbutton.setOnClickListener {
            if(LocationSetData.data.size > 1){
                val intent = Intent(this, MiddleLocationActivity::class.java)
                startActivity(intent)
            }
            else{
            Toast.makeText(this,"2개 이상 위치를 설정하고 중간지점을 검색해주세요!", Toast.LENGTH_LONG).show()
        }

        }
    }

//    private fun getAppKeyHash() {
//        try {
//            val info =
//                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val something = String(Base64.encode(md.digest(), 0))
//                Log.e("Hash key", something)
//            }
//        } catch (e: Exception) {
//            Log.e("name not found", e.toString())
//        }
//    }

    private fun changeIntToDP(value: Int): Int {

        val displayMetrics = resources.displayMetrics
        return (value * displayMetrics.density).roundToInt()
    }

    private fun dynamicButtonCreate() {


        viewmodel.dynamicButtonArray.add(
            viewmodel.dynamicCreateChooseNum,
            viewmodel.dynamicCreateChooseNum
        )

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

            val userChooseLocation = LocationData(data!!.getStringExtra("UserChooseLocationName"), data.getStringExtra("UserChooseLocationAddress"), data.getDoubleExtra("UserChooseLocationLatitude", 0.0), data.getDoubleExtra("UserChooseLocationLongitude", 0.0))
            for (i in 0..viewmodel.dynamicButtonArray.lastIndex) {
                if (requestCode == viewmodel.dynamicButtonArray[i]) {
                    if(binding.buttonview.findViewById<Button>(i).text != "위치를 입력해주세요"){
                       for(j in 0..LocationSetData.data.size -1) {
                           var removeData =
                               binding.buttonview.findViewById<Button>(j).text.toString()
                           if (LocationSetData.data[j].locationName.equals(removeData)){

                               Log.e("LocationSetData Before", LocationSetData.data.toString())
                               LocationSetData.data.removeAt(j)
                               LocationSetData.data.add(j, userChooseLocation)
                               Log.e("LocationSetData After", LocationSetData.data.toString())
                           }
                       }
                        Log.e("hhhh","COMPLETE")
                    }
                    else {
                        LocationSetData.data.add(userChooseLocation)
                    }
                    binding.buttonview.findViewById<Button>(i).text = userChooseLocation.locationName
                    Log.e("!!!", viewmodel.dynamicButtonArray[i].toString())
                }
            }
        }
    }
}