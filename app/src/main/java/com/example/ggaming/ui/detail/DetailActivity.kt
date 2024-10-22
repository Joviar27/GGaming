package com.example.ggaming.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.core.domain.model.Game
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.theme.GGamingTheme

class DetailActivity : ComponentActivity() {

    private val game by lazy {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(GAME_DETAIL, Game::class.java)
        }else{
            @Suppress("Deprecation")
            intent.getParcelableExtra(GAME_DETAIL) as Game?
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GGamingTheme {
                game?.let {
                    DetailContent(it) { event ->
                        when(event){
                            is GameEvent.BackEvent -> finish()
                            is GameEvent.OnFavoriteClicked ->{
                                //TODO: Add to favorite
                            }
                        }
                    }
                } ?: finish()
            }
        }
    }

    companion object{
        const val GAME_DETAIL = "game-detail"
    }
}
