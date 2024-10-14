package com.example.ggaming.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Result
import com.example.core.domain.usecase.GetGameListUseCase
import com.example.ggaming.ui.PagingGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGameListUseCase: GetGameListUseCase
): ViewModel() {

    private val _showErrorEvent = MutableLiveData<String>()
    val showErrorEvent: LiveData<String> get() = _showErrorEvent

    private val _state = MutableStateFlow(PagingGameState())
    val state: StateFlow<PagingGameState> get() = _state


    init {
        getGameList(null)
    }

    fun getGameList(query: String?){
        viewModelScope.launch {
            when(
                val result = getGameListUseCase
                    .getGameList(query)
            ){
                is Result.Success -> {
                    _state.value = state.value.copy(
                        loading = false,
                        pagingGameList = result.data
                    )
                }
                is Result.Error ->{
                    _showErrorEvent.value = result.message
                }
            }
        }
    }
}