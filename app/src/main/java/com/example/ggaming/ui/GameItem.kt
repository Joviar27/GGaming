package com.example.ggaming.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ggaming.ui.theme.backgroundLight
import com.example.ggaming.ui.theme.onPrimaryContainerLightMediumContrast
import com.example.ggaming.ui.theme.primaryContainerLightMediumContrast
import com.example.core.domain.model.Game
import com.example.core.domain.model.createDummyGame
import com.example.ggaming.R
import com.example.ggaming.ui.theme.GGamingTypography
import com.example.ggaming.ui.theme.onPrimaryContainerLight
import com.example.ggaming.ui.theme.onSurfaceLightHighContrast
import com.example.ggaming.ui.theme.primaryContainerLight
import com.example.ggaming.ui.theme.primaryLight
import com.example.ggaming.ui.theme.surfaceContainerHighLightMediumContrast
import com.example.ggaming.ui.theme.surfaceContainerLightHighContrast
import com.example.ggaming.utils.DateUtils

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GameItem(
    modifier: Modifier = Modifier,
    game: Game,
    event: (GameEvent) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                event.invoke(GameEvent.OnItemClicked(game))
            },
        colors = CardDefaults.cardColors(
            containerColor = primaryContainerLight
        )
    ){
        Column {
            Box(Modifier.wrapContentSize()){
                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(40.dp)),
                    model = game.background,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd)
                        .padding(8.dp),
                    onClick = {
                        event.invoke(GameEvent.OnFavoriteClicked(game))
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if(game.isFavorite) R.drawable.baseline_bookmark_24
                            else R.drawable.baseline_bookmark_border_24
                        ),
                        contentDescription = null,
                        tint = primaryLight
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column{
                    Text(
                        style = GGamingTypography.headlineSmall,
                        color = onPrimaryContainerLight,
                        text = game.name
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        style = GGamingTypography.labelMedium,
                        color = onPrimaryContainerLight,
                        text = stringResource(
                            R.string.release_date,
                            DateUtils.dateToString(
                                date = game.releaseDate,
                                format = "dd MMMM yyyy"
                            )
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RatingText(game.rating)
                    Spacer(Modifier.height(4.dp))
                    RatingCountText(game.ratingCount)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameItemPreview(){
    GameItem(
        game = createDummyGame(),
        event = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1F1A1F)
@Composable
fun RatingTextPreview(){
    RatingText(4.99)
}

@Preview(showBackground = true, backgroundColor = 0xFF1F1A1F)
@Composable
fun RatingCountTextPreview(){
    RatingCountText(9000)
}