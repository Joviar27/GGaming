package com.example.ggaming.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Result
import com.example.core.domain.model.Game
import com.example.core.domain.usecase.FavoriteGameUseCase
import com.example.core.domain.usecase.GameUseCase
import com.example.ggaming.ui.GameDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val favoriteGameUseCase: FavoriteGameUseCase
): ViewModel() {

    private val _state = MutableStateFlow(GameDetailState())
    val state: StateFlow<GameDetailState> get() = _state

    fun getGameDetail(id: String){
        viewModelScope.launch {
            showLoading(true)
            gameUseCase.getGameDetail(id).collect{ result ->
                handleResult(result){
                    _state.value = state.value.copy(
                        game = (result as Result.Success).data
                    )
                }
            }
        }
    }

    fun handleFavoriteClicked(game: Game){
        if(state.value.game?.isFavorite == true){
            removeFavorite(game.id)
        }else addFavorite(game)
    }

    private fun addFavorite(game: Game){
        viewModelScope.launch {
            favoriteGameUseCase.insertFavoriteGame(game).collect{ result ->
                handleResult(result){
                    _state.value = state.value.copy(
                        game = state.value.game?.copy(isFavorite = true)
                    )
                }
            }
        }
    }

    private fun removeFavorite(gameId: String) {
        viewModelScope.launch {
            favoriteGameUseCase.removeFavoriteGame(gameId).collect{ result ->
                handleResult(result){
                    _state.value = state.value.copy(
                        game = state.value.game?.copy(isFavorite = false)
                    )
                }
            }
        }
    }

    private fun <T>handleResult(result: Result<T>, success: () -> Unit){
        when(result){
            is Result.Success ->{
                showLoading(false)
                success.invoke()
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

    private fun showLoading(show: Boolean){
        _state.value = state.value.copy(
            loading = show
        )
    }

    fun dismissError(){
        _state.value = state.value.copy(error = false)
    }
}