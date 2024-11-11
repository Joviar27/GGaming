package com.example.ggaming.favorite.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core.domain.model.Game
import com.example.ggaming.favorite.R
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.layout.GameList
import com.example.ggaming.ui.layout.LoadingBar
import com.example.ggaming.ui.theme.GGamingTypography
import com.example.ggaming.ui.theme.onPrimaryLight
import com.example.ggaming.ui.theme.primaryLight
import com.example.ggaming.R as AppR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteContent(
    gameItems: List<Game>,
    loading: Boolean,
    event: (GameEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = GGamingTypography.headlineMedium,
                        text = stringResource(R.string.favorite_toolbar_title)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryLight,
                    titleContentColor = onPrimaryLight
                ),
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            event.invoke(GameEvent.NavigateBack)
                        },
                        painter = painterResource(AppR.drawable.baseline_arrow_back_24),
                        tint = onPrimaryLight,
                        contentDescription = null
                    )
                }
            )
        }
    ){ innerPadding ->
        if(loading){
            Box(Modifier.fillMaxSize()){
                LoadingBar()
            }
        }else{
            GameList(
                Modifier.padding(innerPadding),
                gameList = gameItems
            ){
                event.invoke(it)
            }
        }
    }
}