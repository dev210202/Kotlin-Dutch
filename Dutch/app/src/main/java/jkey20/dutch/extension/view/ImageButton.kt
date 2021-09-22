package jkey20.dutch.extension.view

import android.widget.EditText
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.dutch2019.base.BaseViewModel
import jkey20.dutch.R
import jkey20.dutch.main.MainFragmentDirections
import jkey20.dutch.search.SearchViewModel

@BindingAdapter(value = ["plus"])
fun plus(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener { view ->
        view.findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToSearchFragment()
        )
    }
}

@BindingAdapter(value = ["search"])
fun search(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener { view ->
        val inputText =
            imageButton.rootView.findViewById<EditText>(R.id.edittext_search).text.toString()
        (viewModel as SearchViewModel).search(inputText)
    }
}