package jkey20.dutch.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import jkey20.dutch.SearchCoroutineRule
import jkey20.dutch.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@ExperimentalCoroutinesApi
class SearchViewModelTest {


    @get:Rule
    var instantTaskExcutorRule = InstantTaskExecutorRule()

    @get:Rule
    var searchCoroutineRule = SearchCoroutineRule()

    private lateinit var viewModel: SearchViewModel


    @Before
    fun setup() {
        // init viewModel
        viewModel = SearchViewModel()
    }

    @Test
    fun `검색 후 리스트 결과값 테스트`() {
        viewModel.search("q")

        val value = viewModel.locationList.getOrAwaitValue().toString()
        assertThat(value).isEqualTo("qqqq")
    }

    @Test
    fun `일반적인 경우 테스트`(){
        viewModel.input("q")
        val value = viewModel.inputValue.getOrAwaitValue()
        assertThat(value).isEqualTo("q")
    }
}