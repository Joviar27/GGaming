package com.example.ggaming.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.Result
import com.example.core.domain.model.Game
import com.example.core.domain.usecase.FavoriteGameUseCase
import com.example.core.domain.usecase.GameUseCase
import com.example.ggaming.ui.state.GameListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val favoriteGameUseCase: FavoriteGameUseCase
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

    fun addFavorite(game: Game){
        viewModelScope.launch {
            showLoading(true)
            favoriteGameUseCase.insertFavoriteGame(game).collect{ result ->
                handleResult(result)
            }
        }
    }

    fun removeFavorite(gameId: String) {
        viewModelScope.launch {
            showLoading(true)
            favoriteGameUseCase.removeFavoriteGame(gameId).collect{ result ->
                handleResult(result)
            }
        }
    }

    fun showLoading(show: Boolean){
        _state.value = state.value.copy(
            loading = show
        )
    }

    fun dismissError(){
        _state.value = state.value.copy(error = false)
    }

    fun showError(message: String){
        _state.value = state.value.copy(
            loading = false,
            errorMessage = message,
            error = true
        )
    }

    private fun <T>handleResult(result: Result<T>){
        when(result){
            is Result.Error ->{
                showError(result.message)
            }
            is Result.Success -> {
                showLoading(false)
            }
        }
    }
}