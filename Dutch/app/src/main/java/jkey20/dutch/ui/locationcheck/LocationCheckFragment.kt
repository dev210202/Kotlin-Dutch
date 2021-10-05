package jkey20.dutch.ui.locationcheck

import android.graphics.BitmapFactory
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import jkey20.dutch.BuildConfig
import jkey20.dutch.R
import jkey20.dutch.databinding.FragmentLocationCheckBinding
import jkey20.dutch.ui.main.MainViewModel
import jkey20.dutch.model.LocationData


class LocationCheckFragment : BaseFragment<FragmentLocationCheckBinding>(
    R.layout.fragment_location_check
) {
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var locationData: LocationData

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        LocationCheckFragmentArgs.fromBundle(requireArguments()).let { data ->
            locationData = data.locationData
            binding.checkMaplayout.addView(mapSetting(locationData))
            binding.name = data.locationData.name
            binding.address = data.locationData.address
        }

        binding.locationsetbutton.setOnClickListener { view ->
            viewModel.addLocation(locationData)
            val navController = view.findNavController()
            navController.popBackStack()
            navController.popBackStack()
        }
    }


    fun mapSetting(data: LocationData): TMapView {
        val markerItemPoint = TMapPoint(data.lat, data.lon)

        val markerImage =
            BitmapFactory.decodeResource(
                requireContext().resources,
                R.drawable.ic_marker_black
            )

        val markerItem = TMapMarkerItem().apply {
            icon = markerImage
            tMapPoint = markerItemPoint
            setPosition(0.5F, 1F)
        }
        return TMapView(context).apply {
            setSKTMapApiKey("${BuildConfig.T_MAP_API}")
            setCenterPoint(data.lon, data.lat)
            addMarkerItem("markerItem", markerItem)
        }
    }
}