package com.example.ggaming.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.ggaming.ui.layout.ErrorBottomSheet
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.detail.DetailActivity
import com.example.ggaming.ui.theme.GGamingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewmodel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initView()
    }

    private fun initView(){
        setContent {
            val state by viewmodel.state.collectAsStateWithLifecycle()
            val pagingGameItems = viewmodel.gamesList.collectAsLazyPagingItems()

            GGamingTheme {
                HomeContent(pagingGameItems){ event ->
                    when(event){
                        is GameEvent.OnFavoriteClicked ->{
                            //TODO: Save to favorite
                        }
                        is GameEvent.OnItemClicked ->{
                            val intent = Intent(this, DetailActivity::class.java).apply {
                                putExtra(DetailActivity.GAME_ID, event.game.id)
                            }
                            startActivity(intent)
                        }
                        is GameEvent.OnSearchValueChanged ->{
                            viewmodel.updateQuery(event.search)
                        }
                    }
                }
            }
            ErrorBottomSheet(
                message = state.errorMessage,
                show = state.error || pagingGameItems.loadState.mediator?.hasError == true
            ) {
                viewmodel.dismissError()
            }
        }
    }
}
