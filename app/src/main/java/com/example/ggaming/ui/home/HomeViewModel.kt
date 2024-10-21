package com.example.ggaming.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.domain.model.Game
import com.example.core.domain.usecase.GetGameListUseCase
import com.example.ggaming.ui.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGameListUseCase: GetGameListUseCase
): ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> get() = _state

    private val _pagingListState = MutableStateFlow(PagingData.empty<Game>())
    val pagingListState: StateFlow<PagingData<Game>> get() = _pagingListState


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
                    _pagingListState.value = result.data
                }
                is Result.Error ->{
                    _state.value = state.value.copy(
                        error = true,
                        errorMessage = result.message
                    )
                }
            }
        }
    }

    fun dismissError(){
        _state.value = state.value.copy(error = false)
    }
}