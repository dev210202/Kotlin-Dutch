package com.dutch2019.ui.setting

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.dutch2019.Adapter.RatioRecyclerAdapter
import com.dutch2019.Data.LocationData
import com.dutch2019.Data.LocationSetData
import com.dutch2019.R
import com.dutch2019.databinding.ActivityMainBinding
import com.dutch2019.databinding.ActivitySettingBinding
import com.dutch2019.ui.main.MainViewModel
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    lateinit var dialog: Dialog
    var SETTING_OK = 22
    var RESET_OK = 23
    lateinit var adapter: RatioRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        adapter = RatioRecyclerAdapter(LocationSetData.data)
        dialog = Dialog(this)
        binding.ratioRecyclerview.adapter = adapter

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var value = 0;
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                value = progress
                binding.seekbarvalueTextview.text = "" + (value + 1) + ":" + (9 - value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.seekbarvalueTextview.text = "" + (value + 1) + ":" + (9 - value)
            }

        })

        binding.resetButton.setOnClickListener {
            dialog.setContentView(R.layout.setting_dialog)
            dialog.show();
        }
        binding.okButton.setOnClickListener {
            var intent = Intent()
            intent.putExtra("ratioArrayList", adapter.getRatioArrayList())
            setResult(SETTING_OK, intent)
            finish()
        }
        binding.ratioRecyclerview.adapter
    }

    fun resetButtonClicked(view: View) {
        dialog.dismiss()
        var intent = Intent()
        setResult(RESET_OK, intent)
        finish()
    }

}