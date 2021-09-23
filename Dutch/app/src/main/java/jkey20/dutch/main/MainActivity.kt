package jkey20.dutch.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import jkey20.dutch.BuildConfig
import jkey20.dutch.R
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tmapApi = TMapTapi(this)
        tmapApi.setSKTMapAuthentication("${BuildConfig.TMAP_API}")
    }
}