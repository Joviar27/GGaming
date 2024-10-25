package com.example.ggaming.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.layout.ErrorBottomSheet
import com.example.ggaming.ui.theme.GGamingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    private val viewModel: DetailViewModel by viewModels()

    private val gameId by lazy {
        intent.getStringExtra(GAME_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        gameId?.let {
            viewModel.getGameDetail(it)
        } ?: finish()
        initView()
    }

    private fun initView(){
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            GGamingTheme {
                DetailContent(
                    loading = state.loading,
                    game = state.game
                ) { event ->
                    when(event){
                        is GameEvent.BackEvent -> finish()
                        is GameEvent.OnFavoriteClicked ->{
                            viewModel.handleFavoriteClicked(event.game)
                        }
                    }
                }
                ErrorBottomSheet(
                    message = state.errorMessage,
                    show = state.error
                ) {
                    viewModel.dismissError()
                }
            }
        }
    }

    companion object{
        const val GAME_ID = "game-id"
    }
}
