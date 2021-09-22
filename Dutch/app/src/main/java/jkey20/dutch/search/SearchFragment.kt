package jkey20.dutch.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dutch2019.base.BaseFragment
import jkey20.dutch.R
import jkey20.dutch.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    R.layout.fragment_search,
    SearchViewModel::class.java
) {

}