package com.example.ggaming.ui.detail

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.Game
import com.example.core.domain.model.createDummyGame
import com.example.ggaming.R
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.layout.GameDetail
import com.example.ggaming.ui.layout.LoadingBar
import com.example.ggaming.ui.theme.GGamingTypography
import com.example.ggaming.ui.theme.onPrimaryLight
import com.example.ggaming.ui.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    game: Game?,
    loading: Boolean,
    event: (GameEvent) -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { 
                            event.invoke(GameEvent.NavigateBack)
                        },
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        tint = onPrimaryLight,
                        contentDescription = null
                    )
                },
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        style = GGamingTypography.headlineSmall,
                        text = game?.name ?: ""
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryLight,
                    titleContentColor = onPrimaryLight
                )
            )
        }
    ) { innerPadding ->
        if(loading){
            Box(Modifier.fillMaxSize()){
                LoadingBar()
            }
        }else{
            game?.let {
                GameDetail(
                    modifier = Modifier.padding(innerPadding),
                    game = game
                ) {
                    event.invoke(it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview(){
    DetailContent(createDummyGame(),false) { }
}