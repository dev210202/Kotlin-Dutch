package com.dutch2019.ui.ratio

import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import com.dutch2019.R
import com.dutch2019.adapter.RatioRecyclerAdapter
import com.dutch2019.databinding.FragmentRatioBinding
import com.dutch2019.ui.middle.MiddleViewModel

class RatioFragment : BaseFragment<FragmentRatioBinding>(
    R.layout.fragment_ratio
) {
    private val viewModel : MiddleViewModel by activityViewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        RatioFragmentArgs.fromBundle(requireArguments()).let { data ->
            binding.ratioRecyclerview.adapter = RatioRecyclerAdapter()
            (binding.ratioRecyclerview.adapter as RatioRecyclerAdapter).setLocationListData(data.locationdatalist)
        }

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setRatio( "" + (progress + 1) + " : " + (9 - progress))
                binding.seekbarvalueTextview.text = "" + (progress + 1) + " : " + (9 - progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        binding.buttonSettingComplete.setOnClickListener {
            if ((binding.ratioRecyclerview.adapter as RatioRecyclerAdapter).isAChecked && (binding.ratioRecyclerview.adapter as RatioRecyclerAdapter).isBChecked) {

                var pointList = (binding.ratioRecyclerview.adapter as RatioRecyclerAdapter).getRatioPointArrayList()
                viewModel.setRatioPoint(viewModel.getCalculatedRatioPoint(pointList[0], pointList[1])!!)
                viewModel.setRatio("5 : 5")
                val navController = requireView().findNavController()
                navController.popBackStack()
            }
            else {
                Toast.makeText(requireContext(), "2개의 지점을 모두 선택해주세요!", Toast.LENGTH_LONG)
            }
        }

    }
}