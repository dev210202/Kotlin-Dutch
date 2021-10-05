package jkey20.dutch.ui.middle

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import dagger.hilt.android.AndroidEntryPoint
import jkey20.dutch.R
import jkey20.dutch.databinding.FragmentMiddleBinding
import jkey20.dutch.util.marker.MarkerOverlay
import jkey20.dutch.util.marker.mapAutoZoom
import jkey20.dutch.util.marker.markLocationList
import jkey20.dutch.util.marker.markMiddleLocation

@AndroidEntryPoint
class MiddleFragment : BaseFragment<FragmentMiddleBinding>(
    R.layout.fragment_middle
) {
    private val middleViewModel: MiddleViewModel by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MiddleFragmentArgs.fromBundle(requireArguments()).let { data ->
            middleViewModel.setLocationList(data.locationlist)

        }
        middleViewModel.setCenterPoint(middleViewModel.calculateCenterPoint(middleViewModel.getLocationList()))

        val tMapView = TMapView(context)
        markLocationList(tMapView, requireContext(), middleViewModel.getLocationList())
        markMiddleLocation(tMapView, requireContext(), middleViewModel.getCenterPoint())
        setBallonOverlayClickEvent(tMapView, middleViewModel)


        middleViewModel.setCenterPointAdress(middleViewModel.getCenterPoint())
        middleViewModel.setCenterPointNearSubway(middleViewModel.getCenterPoint())

        mapAutoZoom(tMapView, middleViewModel.getLocationList(), middleViewModel.getCenterPoint())

        binding.maplayoutMiddle.addView(tMapView)

        binding.buttonMiddleSafety.setOnClickListener { view ->
            view.findNavController()
                .navigate(MiddleFragmentDirections.actionMiddleFragmentToSafeFragment())
        }
        binding.buttonCheckNearfacility.setOnClickListener { view ->
            view.findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToNearFragment(
                    middleViewModel.getCenterPoint().latitude.toFloat(),
                    middleViewModel.getCenterPoint().longitude.toFloat()
                )
            )

        }

        middleViewModel.centerPointAddress.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleAddress.text = it
        })
        middleViewModel.centerPointNearSubway.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleNearsubway.text = it
        })

        middleViewModel.routeTime.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleRoutetime.text = it
        })

    }

    fun setBallonOverlayClickEvent(tMapView: TMapView, viewModel: MiddleViewModel) {

        tMapView.setOnMarkerClickEvent { _, p1 ->
            val point = p1.tMapPoint
            //   (viewModel as MiddleLocationViewModel).setCenterPoint(point)
            viewModel.setCenterPointAdress(point)
            viewModel.setCenterPointNearSubway(point)
            binding.textviewMiddleResult.text = p1.id
            if (p1.id == "중간지점") {
                binding.textviewMiddleResult.setTextColor(
                    ContextCompat.getColor(tMapView.rootView.context, R.color.orange)
                )
                viewModel.resetRouteTime()
            } else if (p1.id == "비율변경지점") {
                binding.textviewMiddleResult.setTextColor(
                    ContextCompat.getColor(tMapView.rootView.context, R.color.blue)
                )
                viewModel.setRouteTime(viewModel.getCenterPoint(), p1.latitude, p1.longitude)
            } else {
                binding.textviewMiddleResult.setTextColor(
                    ContextCompat.getColor(tMapView.rootView.context, R.color.black)
                )
                viewModel.setRouteTime(viewModel.getCenterPoint(), p1.latitude, p1.longitude)
            }
        }

    }

}