package com.example.ggaming.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core.domain.model.Game
import com.example.core.domain.model.createDummyGame
import com.example.ggaming.R
import com.example.ggaming.ui.theme.GGamingTypography
import com.example.ggaming.ui.theme.onPrimaryContainerLight
import com.example.ggaming.ui.theme.onSecondaryContainerLight
import com.example.ggaming.ui.theme.onSurfaceLight
import com.example.ggaming.ui.theme.onTertiaryContainerLight
import com.example.ggaming.ui.theme.onTertiaryLight
import com.example.ggaming.ui.theme.primaryContainerLight
import com.example.ggaming.ui.theme.primaryLight
import com.example.ggaming.ui.theme.secondaryContainerLight
import com.example.ggaming.ui.theme.surfaceLight
import com.example.ggaming.ui.theme.tertiaryContainerLight
import com.example.ggaming.ui.theme.tertiaryLight
import com.example.ggaming.utils.DateUtils

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GameDetail(
    game: Game,
    event: (GameEvent.OnFavoriteClicked) -> Unit
) {
    Column(
        Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(surfaceLight),
    ) {
        Box(modifier = Modifier.fillMaxWidth()
                .height(200.dp)
        ){
            GlideImage(
                modifier = Modifier.fillMaxSize(),
                model = game.background,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Box(Modifier.padding(16.dp)
                .clip(CircleShape)
                .background(tertiaryContainerLight)
                .align(Alignment.TopEnd)
                .clickable {
                    event.invoke(GameEvent.OnFavoriteClicked(game))
                }
            ){
                Icon(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(
                        if(game.isFavorite) R.drawable.baseline_bookmark_36
                        else R.drawable.baseline_bookmark_border_36
                    ),
                    tint = onTertiaryContainerLight,
                    contentDescription = null
                )
            }
        }
        LazyRow(
            Modifier.fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(game.genres){
                GenreChip(it.name)
            }
        }
        Row(
            Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = game.name,
                color = onSurfaceLight,
                style = GGamingTypography.headlineMedium
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(
                    R.string.release_date,
                    DateUtils.dateToString(
                        game.releaseDate,
                        "dd MMM yyyy"
                    )
                ),
                style = GGamingTypography.labelLarge
            )
        }
        Row(Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingText(game.rating)
                Spacer(Modifier.width(12.dp))
                RatingCountText(game.ratingCount)
            }
            MetacriticText(game.metacritic)
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = stringResource(R.string.gallery),
            style = GGamingTypography.headlineSmall
        )
        LazyRow(Modifier.padding(start = 16.dp, top = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(game.screenShoots){
                ScreenShootItem(it.image)
            }
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = stringResource(R.string.platform),
            style = GGamingTypography.headlineSmall
        )
        LazyRow(Modifier.padding(start = 16.dp, top = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(game.platforms){
                CarouselItem(it.name, it.image)
            }
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = stringResource(R.string.store),
            style = GGamingTypography.headlineSmall
        )
        LazyRow(Modifier.padding(start = 16.dp, top = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(game.stores){
                CarouselItem(it.name, it.image)
            }
        }
    }
}

@Composable
fun GenreChip(genreName: String){
    Box(Modifier.clip(RoundedCornerShape(8.dp))
        .background(secondaryContainerLight)
    ){
        Text(
            modifier = Modifier.padding(8.dp),
            text = genreName,
            color = onSecondaryContainerLight
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ScreenShootItem(image: String){
    GlideImage(
        modifier = Modifier.width(250.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp)),
        model = image,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CarouselItem(
    name: String,
    image: String
){
    Card(
        modifier = Modifier.width(150.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = primaryContainerLight)
    ) {
        GlideImage(
            modifier = Modifier.fillMaxWidth()
                .height(110.dp),
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = name,
            style = GGamingTypography.titleSmall,
            color = onPrimaryContainerLight
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GameDetailPreview(){
    GameDetail(createDummyGame()){}
}