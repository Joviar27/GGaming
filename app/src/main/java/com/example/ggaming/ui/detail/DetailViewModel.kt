package com.example.ggaming.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Result
import com.example.core.domain.usecase.GetGameDetailUseCase
import com.example.ggaming.ui.GameDetailState
import com.example.ggaming.ui.GameListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getGameDetailUseCase: GetGameDetailUseCase
): ViewModel() {

    private val _state = MutableStateFlow(GameDetailState())
    val state: StateFlow<GameDetailState> get() = _state

    fun getGameDetail(name: String){
        viewModelScope.launch {
            showLoading(true)
            getGameDetailUseCase.getGameDetail(name).collect{ result ->
                when(result){
                    is Result.Success ->{
                        showLoading(false)
                        _state.value = state.value.copy(
                            game = result.data
                        )
                    }
                    is Result.Error ->{
                        showLoading(false)
                        _state.value = state.value.copy(
                            errorMessage = result.message,
                            error = true
                        )
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean){
        _state.value = state.value.copy(
            loading = show
        )
    }

    fun dismissError(){
        _state.value = state.value.copy(error = false)
    }
}