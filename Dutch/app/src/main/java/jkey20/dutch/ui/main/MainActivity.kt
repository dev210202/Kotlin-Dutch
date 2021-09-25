package jkey20.dutch.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import jkey20.dutch.BuildConfig
import jkey20.dutch.R
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tmapApi = TMapTapi(this)
        tmapApi.setSKTMapAuthentication("${BuildConfig.TMAP_API}")
    }
}