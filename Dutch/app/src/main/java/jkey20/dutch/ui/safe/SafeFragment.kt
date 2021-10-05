package jkey20.dutch.ui.safe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import jkey20.dutch.R
import jkey20.dutch.databinding.FragmentSafeBinding

@AndroidEntryPoint
class SafeFragment : BaseFragment<FragmentSafeBinding>(
    R.layout.fragment_safe
) {
    private val safeViewModel : SafeViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  safeViewModel.getAddressID("서울특별시 광진구 군자동 98")
        safeViewModel.loadZipCode("서울특별시 광진구 군자동 98")

        safeViewModel.zipCode.observe(viewLifecycleOwner, Observer {
            zipCode ->
            Log.i("ZIPCODE", zipCode
            )
            safeViewModel.loadSafetyIndex(zipCode)
        })
        safeViewModel.safetyIndex.observe(viewLifecycleOwner, Observer {
            safetyIndex ->
            Log.i("SAFETYINDEX", safetyIndex)
            binding.textviewSafeSafetyindex.text = safetyIndex
        })

    }
}