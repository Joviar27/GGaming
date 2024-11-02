package com.example.ggaming.favorite.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ggaming.di.FavoriteModuleDependencies
import com.example.ggaming.favorite.ViewModelFactory
import com.example.ggaming.favorite.di.DaggerFavoriteComponent
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.detail.DetailActivity
import com.example.ggaming.ui.layout.ErrorBottomSheet
import com.example.ggaming.ui.theme.GGamingTheme
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity: ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initView()
    }

    private fun inject(){
        DaggerFavoriteComponent.builder()
            .context(this)
            .addDependencies(EntryPointAccessors.fromApplication(
                applicationContext,
                FavoriteModuleDependencies::class.java
            ))
            .build()
            .inject(this)
    }

    private fun initView(){
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            GGamingTheme {
                FavoriteContent(
                    state.gameList,
                    state.loading
                ){ event ->
                    when(event){
                        is GameEvent.OnFavoriteClicked ->{
                            val game = event.game
                            if(game.isFavorite) viewModel.removeFavorite(game.id)
                            else viewModel.addFavorite(game)
                        }
                        is GameEvent.OnItemClicked ->{
                            val intent = Intent(this, DetailActivity::class.java).apply {
                                putExtra(DetailActivity.GAME_ID, event.game.id)
                            }
                            startActivity(intent)
                        }
                        is GameEvent.NavigateBack -> finish()
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
}