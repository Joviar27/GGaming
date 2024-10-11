package com.example.ggaming.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.domain.model.Game
import com.example.core.domain.model.createDummyGameList
import com.example.ggaming.ui.theme.surfaceContainerHighLight
import com.example.ggaming.ui.theme.tertiaryLight
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PagingGameList(
    gamePagingItems: LazyPagingItems<Game>,
    onLoadMore: Boolean,
    event: (GameEvent) -> Unit
) {
    LazyColumn(
        Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(gamePagingItems.itemCount){ index ->
            gamePagingItems[index]?.let {
                GameItem(
                    game = it,
                    event = event
                )
            }
        }
        item {
            LoadMoreLoadingItem(onLoadMore)
        }
    }
}

@Composable
fun LoadMoreLoadingItem(show: Boolean){
    Box(
        Modifier.fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(32.dp)
                .padding(top = 8.dp),
            color = tertiaryLight,
            trackColor = surfaceContainerHighLight
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PagingGameListPreview(){
    val dummyPaging = MutableStateFlow(PagingData.from(createDummyGameList(2)))
    val dummy = dummyPaging.collectAsLazyPagingItems()
    PagingGameList(
        gamePagingItems = dummy,
        onLoadMore = true
    ){ }
}

@Preview(showBackground = true)
@Composable
fun LoadingItemPreview(){
    LoadMoreLoadingItem(true)
}