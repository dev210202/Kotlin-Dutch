package jkey20.dutch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import jkey20.dutch.R
import jkey20.dutch.databinding.FragmentMainBinding
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(
    R.layout.fragment_main
) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.layoutMainPlus.setOnClickListener { view ->
            view.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSearchFragment()
            )
        }
        binding.imagebuttonMainPlus.setOnClickListener {view ->
            view.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSearchFragment()
            )
        }
    }
}