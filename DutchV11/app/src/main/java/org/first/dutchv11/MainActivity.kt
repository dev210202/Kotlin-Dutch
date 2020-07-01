package org.first.dutchv11

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import org.first.dutchv11.databinding.ActivityMainBinding
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var dynamicCreateChooseNum = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAppKeyHash()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.serachlocationbutton1.setOnClickListener {
            var intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivity(intent)
        }
        binding.serachlocationbutton2.setOnClickListener {
            var intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivity(intent)
        }
        binding.plusbutton.setOnClickListener {

            dynamicButtonCreate()

        }
        binding.searchmiddlelocationbutton.setOnClickListener {
            checkMinimumPosition()
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
    private fun changeDP(value : Int) : Int{
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }

    private fun dynamicButtonCreate(){
        dynamicCreateChooseNum++
        val buttonText = (dynamicCreateChooseNum + 2).toString() + "번 위치를 입력해주세요"
        val dynamicButton = Button(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(changeDP(10), changeDP(20), changeDP(10), 0)
        dynamicButton.layoutParams = layoutParams
        dynamicButton.setText(buttonText)
        dynamicButton.setBackgroundResource(R.drawable.button_main_location_search)
        dynamicButton.id = dynamicCreateChooseNum
        dynamicButton.setOnClickListener {
            var intent = Intent(applicationContext, SearchLocationActivity::class.java)
            startActivity(intent)
        }
        binding.buttonview.addView(dynamicButton)
    }

    private fun checkMinimumPosition(){
    }

}
