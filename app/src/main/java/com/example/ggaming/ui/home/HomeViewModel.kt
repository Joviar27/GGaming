package com.example.ggaming.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.domain.usecase.GameUseCase
import com.example.ggaming.ui.GameListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
): ViewModel() {
    private val _query = MutableStateFlow("")

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val gamesList = _query.flatMapLatest { query ->
        gameUseCase.getGameList(query).cachedIn(viewModelScope)
    }

    private val _state = MutableStateFlow(GameListState())
    val state: StateFlow<GameListState> get() = _state

    fun dismissError(){
        _state.value = state.value.copy(error = false)
    }
}