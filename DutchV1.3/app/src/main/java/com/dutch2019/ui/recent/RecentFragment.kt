package com.dutch2019.ui.recent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dutch2019.R
import com.dutch2019.ui.nearfacillity.NearFacilityFragmentArgs

class RecentFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var locationInfo = RecentFragmentArgs.fromBundle(requireArguments()).let { data ->
            data.locationlist.forEach {
                Log.i("LocationInfo RECENT", it.name)
            }
        }
    }


}