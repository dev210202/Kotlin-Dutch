package com.dutch2019.ui.near

import android.content.Intent
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.Button
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.adapter.EmptyDataObserver
import com.dutch2019.adapter.NearRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentNearBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.*
import com.dutch2019.util.marker.*
import com.skt.Tmap.TMapView

class NearFragment : BaseFragment<FragmentNearBinding>(R.layout.fragment_near) {
    private val vm: MiddleViewModel by activityViewModels()
    private val emptyDataObserver by lazy {
        EmptyDataObserver(binding.rvNearFacility, binding.tvEmpty)
    }
    private val nearRecyclerAdapter by lazy {
        NearRecyclerAdapter(onItemClicked = { locationData ->
            removeAllBallon(tMapView)
            tMapView.getMarkerItem2FromID(locationData.name).apply {
                onSingleTapUp(PointF(), tMapView)
            }
            ButtonState.ACTIVE.changeButton(binding.btnShare)
        }, onInternetClicked = { locationData ->
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://m.search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=${locationData.name}")
            )
            startActivity(intent)
        }).apply {
            registerAdapterDataObserver(emptyDataObserver)
        }
    }
    private val chipOnClickListener by lazy {
        ChipOnClickListener()
    }
    lateinit var tMapView: TMapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.loadShareImage()

        initTMapView()
        initChipButtons()
        initButtonLeftArrow()
        initButtonShare()

        vm.facilityList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                setEmptyViewVisible()
            } else {
                mapAutoZoom(tMapView, vm.getFacilityList(), vm.getSearchPoint())
            }
            setAdapterList(list)
            markNearFacilityList(tMapView, requireContext(), list)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        vm.clearFacilityList()
    }

    private fun initButtonShare() {
        binding.btnShare.setOnClickListener {
            vm.getSelectedLocation().apply {
                vm.shareKakao(location = this, context = requireContext())
            }
        }
    }

    private fun initButtonLeftArrow() {
        binding.ibLeftArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initChipButtons() {
        binding.btnTransport.setOnClickListener(chipOnClickListener)
        binding.btnCulture.setOnClickListener(chipOnClickListener)
        binding.btnFood.setOnClickListener(chipOnClickListener)
        binding.btnCafe.setOnClickListener(chipOnClickListener)
    }

    private fun setAdapterList(list: List<LocationData>) {
        nearRecyclerAdapter.run {
            binding.rvNearFacility.adapter = this
            this.setLocationDataList(list)
        }
    }

    private fun initTMapView() {
        tMapView = TMapView(context).apply {
            markLocationList(this, requireContext(), vm.getLocationList())
            markMiddleLocation(this, requireContext(), vm.getCenterPoint())
            markRatioLocation(this, requireContext(), vm.getRatioPoint())
            mapAutoZoom(this, vm.getLocationList(), vm.getSearchPoint())
            setMarkerClickEvent(this)
            binding.layoutNear.addView(this)
        }
    }

    inner class ChipOnClickListener : OnClickListener {
        override fun onClick(view: View) {
            setOtherChipsStateDefault()
            ButtonState.ACTIVE.changeButton(view as Button)
            ButtonState.DISABLE.changeButton(binding.btnShare)
            removeAllNearFacilityMark(tMapView, vm.getFacilityList())
            setEmptyViewInvisible()
            vm.clearSelectedLocation()
            vm.searchNearFacility(vm.getSearchPoint(), getSearchKeyWords(view.text.toString()))
            nearRecyclerAdapter.setSelectedPosition(-1)
        }
    }


    private fun getSearchKeyWords(text: String) = when (text) {
        "대중교통" -> {
            Category.TRANSPORT.getSearchKeyWords()
        }
        "음식점" -> {
            Category.FOOD.getSearchKeyWords()
        }
        "카페" -> {
            Category.CAFE.getSearchKeyWords()
        }
        "문화시설" -> {
            Category.CULTURE.getSearchKeyWords()
        }
        else -> {
            ""
        }
    }

    private fun setOtherChipsStateDefault() {
        ButtonState.DEFAULT.apply {
            changeButton(binding.btnTransport)
            changeButton(binding.btnCafe)
            changeButton(binding.btnFood)
            changeButton(binding.btnCulture)
        }
    }

    private fun setMarkerClickEvent(tMapView: TMapView) {
        tMapView.setOnMarkerClickEvent { _, tMapMarkerItem2 ->
            changeDefaultNearMarks(
                tMapView,
                requireContext(),
                vm.getFacilityList(),
                tMapMarkerItem2
            )
            tMapMarkerItem2.tMapPoint.apply {
                vm.setSearchPoint(this)
                tMapView.setCenterPoint(this.longitude, this.latitude)
            }

            if (isNearFacilityMarker(tMapMarkerItem2.id, vm.getLocationList())) {
                vm.getIndexToFacilityList(tMapView.centerPoint, tMapMarkerItem2.id).apply {
                    binding.rvNearFacility.scrollToPosition(this)
                    nearRecyclerAdapter.setSelectedPosition(this)
                }
                tMapMarkerItem2.icon = Marker.NEAR_PRIMARY.getMark(requireContext()).toBitmap()
                findSameLocationDataFromMarkerItem(tMapMarkerItem2, vm.getFacilityList())
            } else {
                if (tMapMarkerItem2.id == "중간지점") {
                    LocationData(
                        name = "중간지점",
                        address = vm.getCenterPointAddressValue(),
                        lat = vm.getCenterPoint().latitude,
                        lon = vm.getCenterPoint().longitude
                    )
                } else {
                    findSameLocationDataFromMarkerItem(tMapMarkerItem2, vm.getLocationList())
                }
            }.apply {
                vm.setSelectedLocation(this)
            }

            ButtonState.ACTIVE.changeButton(binding.btnShare)
        }
    }

    private fun setEmptyViewVisible() {
        binding.tvEmpty.visibility = VISIBLE
    }

    private fun setEmptyViewInvisible() {
        binding.tvEmpty.visibility = INVISIBLE
    }
}

