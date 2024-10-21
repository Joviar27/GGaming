package com.example.ggaming.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.ggaming.ui.ErrorBottomSheet
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.theme.GGamingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewmodel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

        initView()
    }

    private fun initView(){
        setContent {
            val state by viewmodel.state.collectAsStateWithLifecycle()
            val pagingGameItems = viewmodel.pagingListState.collectAsLazyPagingItems()

            GGamingTheme {
                HomeContent(pagingGameItems){ event ->
                    when(event){
                        is GameEvent.OnFavoriteClicked ->{
                            //TODO: Save to favorite
                        }
                        is GameEvent.OnItemClicked ->{
                            //TODO: Go to detail page
                        }
                        is GameEvent.OnSearchValueChanged ->{
                            viewmodel.getGameList(event.search)
                        }
                    }
                }
            }
            ErrorBottomSheet(
                message = state.errorMessage,
                show = state.error
            ) {
                viewmodel.dismissError()
            }
        }
    }
}
