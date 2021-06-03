package com.dutch2019.extension

import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import com.dutch2019.base.BaseViewModel
import com.dutch2019.ui.ratio.RatioViewModel

@BindingAdapter(value = ["seekbarclick"])

fun seekBarClick(seekBar: SeekBar, viewModel: BaseViewModel){
    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            (viewModel as RatioViewModel).setRatio("" + (progress + 1) + ":" + (9 - progress))
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        //    (viewModel as RatioViewModel).setRatio("" + (progress + 1) + ":" + (9 - progress))
        }

    })
}