package org.first.dutchv11

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import org.first.dutchv11.databinding.ActivityMainBinding
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAppKeyHash()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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

}
