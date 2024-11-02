package com.example.ggaming.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Result
import com.example.core.domain.model.Game
import com.example.core.domain.usecase.FavoriteGameUseCase
import com.example.ggaming.ui.state.GameListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteGameUseCase: FavoriteGameUseCase
): ViewModel() {

    private val _state = MutableStateFlow(GameListState())
    val state: StateFlow<GameListState> get() = _state

    init {
        getFavoriteGames()
    }

    private fun getFavoriteGames(){
        viewModelScope.launch {
            showLoading(true)
            favoriteGameUseCase.getFavoriteGameList().collect{ result ->
                handleResult(result){
                    _state.value = state.value.copy(
                        gameList = (result as Result.Success).data
                    )
                }
            }
        }
    }

    fun addFavorite(game: Game){
        viewModelScope.launch {
            favoriteGameUseCase.insertFavoriteGame(game).collect{ result ->
                handleResult(result){}
            }
        }
    }

    fun removeFavorite(gameId: String) {
        viewModelScope.launch {
            favoriteGameUseCase.removeFavoriteGame(gameId).collect{ result ->
                handleResult(result){}
            }
        }
    }

    private fun showLoading(show: Boolean){
        _state.value = state.value.copy(
            loading = show
        )
    }

    private fun showError(message: String){
        _state.value = state.value.copy(
            loading = false,
            errorMessage = message,
            error = true
        )
    }

    fun dismissError(){
        _state.value = state.value.copy(error = false)
    }

    private fun <T>handleResult(result: Result<T>, success: () -> Unit){
        when(result){
            is Result.Success ->{
                showLoading(false)
                success.invoke()
            }
            is Result.Error ->{
                showError(result.message)
            }
        }
    }
}