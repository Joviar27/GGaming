package com.example.ggaming.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.ggaming.R
import com.example.ggaming.ui.layout.ErrorBottomSheet
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.detail.DetailActivity
import com.example.ggaming.ui.theme.GGamingTheme
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
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

            LaunchedEffect(pagingGameItems.loadState) {
                if(pagingGameItems.loadState.mediator?.hasError == true){
                    viewmodel.showError("")
                }
            }

            GGamingTheme {
                HomeContent(pagingGameItems){ event ->
                    when(event){
                        is GameEvent.OnFavoriteClicked ->{
                            val game = event.game
                            if(game.isFavorite) viewmodel.removeFavorite(game.id)
                            else viewmodel.addFavorite(game)
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
                        is GameEvent.NavigateFavorite ->{
                            installFavoriteFeature()
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

    private fun installFavoriteFeature() {
        val favoriteModule = "favorite"
        val manager = SplitInstallManagerFactory.create(this)
        if (manager.installedModules.contains(favoriteModule)) {
            navigateToFavorite()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(favoriteModule)
                .build()
            val listener = SplitInstallStateUpdatedListener {
                when (it.status()) {
                    SplitInstallSessionStatus.DOWNLOADING ->{
                        viewmodel.showLoading(true)
                    }
                    SplitInstallSessionStatus.INSTALLED -> {
                        navigateToFavorite()
                    }
                    SplitInstallSessionStatus.FAILED -> {
                        viewmodel.showError(getString(R.string.error_install_module))
                    }
                    else -> Unit
                }
            }
            manager.registerListener(listener)
            manager.startInstall(request).addOnFailureListener {
                viewmodel.showError(getString(R.string.error_install_module))
            }
        }
    }

    private fun navigateToFavorite(){
        val uri = Uri.parse("ggaming://favorites")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}
