package com.example.ggaming.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.Game
import com.example.core.domain.model.createDummyGameList
import com.example.ggaming.ui.GameEvent

@Composable
fun GameList(
    gameList: List<Game>,
    event: (GameEvent) -> Unit
) {
    LazyColumn(
        Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(gameList){
            GameItem(
                game = it,
                event = event
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameListPreview(){
    GameList(
        gameList = createDummyGameList(3)
    ){ }
}