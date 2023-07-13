package com.dutch2019.ui.ratio

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.base.BaseFragment
import com.dutch2019.R
import com.dutch2019.databinding.FragmentRatioBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.*
import com.dutch2019.util.marker.*
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapView

class RatioFragment : BaseFragment<FragmentRatioBinding>(
    R.layout.fragment_ratio
) {
    private val vm: MiddleViewModel by activityViewModels()
    private val tMapView by lazy { TMapView(context) }
    lateinit var markerItemABitmap: Bitmap
    lateinit var markerItemBBitmap: Bitmap
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        markLocationList(tMapView, requireContext(), vm.getLocationList())
        mapAutoZoom(tMapView, vm.getLocationList(), vm.getSearchPoint())
        setMarkerClickEvent(tMapView)

        binding.layoutRatio.addView(tMapView)
        binding.btnCloseA.setOnClickListener(OnCloseClickListener())
        binding.btnCloseB.setOnClickListener(OnCloseClickListener())
        binding.btnRatioComplete.setOnClickListener { button ->
            if (button.isSelected) {
                findNavController().navigate(RatioFragmentDirections.actionRatioFragmentToRatioSelectFragment())
            }
        }
    }

    inner class OnCloseClickListener : OnClickListener {
        override fun onClick(view: View?) {
            var id = ""
            lateinit var iconBitmap: Bitmap
            lateinit var textView: TextView
            lateinit var markerLayout: FrameLayout
            when (view) {
                binding.btnCloseA -> {
                    id = binding.tvNameA.text.toString()
                    textView = binding.tvNameA
                    markerLayout = binding.layoutMarkerA
                    iconBitmap = markerItemABitmap
                    vm.clearSetRatioLocationA()
                }
                binding.btnCloseB -> {
                    id = binding.tvNameB.text.toString()
                    textView = binding.tvNameB
                    markerLayout = binding.layoutMarkerB
                    iconBitmap = markerItemBBitmap
                    vm.clearSetRatioLocationB()
                }
            }
            tMapView.getMarkerItem2FromID(id).icon = iconBitmap
            setDefaultLocationItem(requireContext(), textView, markerLayout)
            removeAllBallon(tMapView)
            setButtonState(binding.btnRatioComplete, ButtonState.DISABLE)
        }

    }

    private fun setMarkerClickEvent(tMapView: TMapView) {
        tMapView.setOnMarkerClickEvent { _, tMapMarkerItem ->
            var location = LocationData()
            vm.getLocationList().forEach { locationData ->
                if (locationData.lat == tMapMarkerItem.latitude && locationData.lon == tMapMarkerItem.longitude && locationData.name == tMapMarkerItem.id) {
                    location = locationData
                }
            }
            if (vm.isNotSetRatioLocationA()) {
                markerItemABitmap = tMapMarkerItem.icon

                vm.setRatioLocationA(location)
                changeSelectRatioMark(tMapMarkerItem, requireContext())
                drawTextOnMarker(requireContext(), tMapMarkerItem.icon, "A")
                binding.tvNameA.text = location.name
                binding.tvNameA.setTextColor(getChangedTextColor(requireContext()))
                binding.layoutMarkerA.background = getSelectRatioMarkerBackground(requireContext())

            } else if (vm.isNotSetRatioLocationB()) {
                markerItemBBitmap = tMapMarkerItem.icon
                vm.setRatioLocationB(location)
                changeSelectRatioMark(tMapMarkerItem, requireContext())
                drawTextOnMarker(requireContext(), tMapMarkerItem.icon, "B")

                binding.tvNameB.text = location.name
                binding.tvNameB.setTextColor(getChangedTextColor(requireContext()))
                binding.layoutMarkerB.background = getSelectRatioMarkerBackground(requireContext())
            } else {
                // Toast
            }

            if (!vm.isNotSetRatioLocationA() && !vm.isNotSetRatioLocationB()) {
                setButtonState(binding.btnRatioComplete, ButtonState.DEFAULT)
            }
        }
    }
}