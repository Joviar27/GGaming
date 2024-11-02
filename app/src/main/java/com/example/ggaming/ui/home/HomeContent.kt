package com.example.ggaming.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.domain.model.Game
import com.example.core.domain.model.createDummyGameList
import com.example.ggaming.R
import com.example.ggaming.ui.GameEvent
import com.example.ggaming.ui.layout.LoadingBar
import com.example.ggaming.ui.layout.PagingGameList
import com.example.ggaming.ui.theme.GGamingTypography
import com.example.ggaming.ui.theme.onPrimaryLight
import com.example.ggaming.ui.theme.primaryLight
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    pagingGameItems: LazyPagingItems<Game>,
    event: (GameEvent) -> Unit
){
    var search by rememberSaveable {
        mutableStateOf(" ")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = GGamingTypography.headlineMedium,
                        text = stringResource(R.string.app_name)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryLight,
                    titleContentColor = onPrimaryLight
                ),
                actions = {
                    IconButton(onClick = {
                        event.invoke(GameEvent.NavigateFavorite)
                    }) {
                        Icon(
                            painterResource(R.drawable.baseline_bookmark_36),
                            null,
                            tint = onPrimaryLight)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)){
            Column(
                Modifier.fillMaxSize()
                    .padding(16.dp)
            ) {
                TextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = search,
                    maxLines = 1,
                    textStyle = GGamingTypography.bodyMedium,
                    label = {
                        Text(
                            style = GGamingTypography.labelLarge,
                            text = stringResource(R.string.search_label)
                        )
                    },
                    onValueChange = {
                        search = it
                        event.invoke(GameEvent.OnSearchValueChanged(it))
                    }
                )
                PagingGameList(
                    gamePagingItems = pagingGameItems,
                    onLoadMore = pagingGameItems.loadState.append is LoadState.Loading
                ) {
                    event.invoke(it)
                }
            }
            if(pagingGameItems.loadState.refresh is LoadState.Loading){
                LoadingBar()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    val dummyPaging = MutableStateFlow(PagingData.from(createDummyGameList(2)))
    val dummy = dummyPaging.collectAsLazyPagingItems()
    HomeContent(dummy){}
}