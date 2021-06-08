package com.dutch2019.ui.ratio

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dutch2019.R
import com.dutch2019.adapter.RatioRecyclerAdapter
class RatioActivity : AppCompatActivity() {
/*
    private lateinit var binding: ActivityRatioBinding
    lateinit var dialog: Dialog
    var SETTING_OK = 22
    var RESET_OK = 23
    var value = 4;
    lateinit var adapter: RatioRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratio)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ratio)
//        adapter =
//            RatioRecyclerAdapter(LocationSetData.data)
        dialog = Dialog(this)
        binding.ratioRecyclerview.adapter = adapter

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

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

            var pointArray = adapter.getRatioPointArrayList()
            if (pointArray.isEmpty()) {
                Toast.makeText(this, "A와 B지점을 모두 선택해주세요!", Toast.LENGTH_LONG).show()
            } else {
                var intent = Intent()

                intent.putExtra("progressValue", value + 1)

                intent.putExtra("APointLat", pointArray[0].latitude)
                intent.putExtra("APointLon", pointArray[0].longitude)
                intent.putExtra("BPointLat", pointArray[1].latitude)
                intent.putExtra("BPointLon", pointArray[1].longitude)

                setResult(SETTING_OK, intent)
                finish()
            }
        }
    }

    fun resetButtonClicked(view: View) {
        dialog.dismiss()
        var intent = Intent()
        setResult(RESET_OK, intent)
        finish()
    }

    fun noButtonClicked(view: View){
        dialog.dismiss()
    }
*/
}