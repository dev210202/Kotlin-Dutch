package jkey20.dutch.locationcheck

import android.graphics.BitmapFactory
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import com.dutch2019.base.BaseFragment
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import jkey20.dutch.BuildConfig
import jkey20.dutch.R
import jkey20.dutch.databinding.FragmentLocationCheckBinding
import jkey20.dutch.main.MainViewModel
import jkey20.dutch.model.LocationData
import jkey20.dutch.search.SearchViewModel


class LocationCheckFragment : BaseFragment<FragmentLocationCheckBinding>(
    R.layout.fragment_location_check
) {
    private val viewModel: MainViewModel by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LocationCheckFragmentArgs.fromBundle(requireArguments()).let { data ->
            binding.checkMaplayout.addView(mapSetting(data.locationData))
            binding.name = data.locationData.name
            binding.address = data.locationData.address
        }


    }


    fun mapSetting(data: LocationData): TMapView {
        val markerItemPoint = TMapPoint(data.lat, data.lon)

        val markerImage =
            (ResourcesCompat.getDrawable(
                requireContext().resources,
                R.drawable.ic_marker_black,
                null
            ) as VectorDrawable).toBitmap()

        val markerItem = TMapMarkerItem().apply {
            icon = markerImage
            tMapPoint = markerItemPoint
            setPosition(0.5F, 1F)
        }
        return TMapView(context).apply {
            setSKTMapApiKey("${BuildConfig.TMAP_API}")
            setCenterPoint(data.lon, data.lat)
            addMarkerItem("markerItem", markerItem)
        }
    }
}