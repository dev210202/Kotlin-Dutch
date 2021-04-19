package com.dutch2019.extension

import androidx.databinding.BindingAdapter
import com.dutch2019.base.BaseViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

@BindingAdapter(value = ["adview"])
fun adView(adView : AdView, viewModel: BaseViewModel){
    var adRequest = AdRequest.Builder().build()
    adView.loadAd(adRequest)

}