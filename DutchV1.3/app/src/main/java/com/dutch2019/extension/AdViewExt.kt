package com.dutch2019.extension

import androidx.databinding.BindingAdapter
import com.dutch2019.base.BaseViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds




@BindingAdapter(value = ["adview"])
fun adView(adView : AdView, viewModel: BaseViewModel){
    MobileAds.initialize(adView.context)
    var adRequest = AdRequest.Builder().build()
    adView.loadAd(adRequest)

}