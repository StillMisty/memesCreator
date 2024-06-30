package top.stillmisty.memescreator.data.viewModel

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import top.stillmisty.memescreator.data.api.Api

class HomeViewModel : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    private val _uiState = MutableStateFlow<List<String>?>(null)
    val uiState: MutableStateFlow<List<String>?> = _uiState
    var lazyListState = LazyListState()

    init {
        getMemeInfo()
    }


    private fun getMemeInfo() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val memeList = Api.retrofitService.getMemesList()
                _uiState.value = memeList
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}