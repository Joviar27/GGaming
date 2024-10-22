package com.example.ggaming.ui.detail

import android.os.Bundle
import android.util.Log
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

    private val gameName by lazy {
        intent.getStringExtra(GAME_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        gameName?.let {
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
                            //TODO: Add to favorite
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
        const val GAME_NAME = "game-name"
    }
}
