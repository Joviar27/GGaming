package com.example.ggaming.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ggaming.R
import com.example.ggaming.ui.theme.GGamingTypography
import com.example.ggaming.ui.theme.errorContainerLight
import com.example.ggaming.ui.theme.onSurfaceLight
import com.example.ggaming.ui.theme.primaryLight

@Composable
fun RatingText(
    modifier: Modifier = Modifier,
    rating: Double,
    color: Color = onSurfaceLight
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_star_24),
            contentDescription = null
        )
        Spacer(Modifier.width(2.dp))
        Text(
            text = rating.toString(),
            style = GGamingTypography.bodyLarge,
            color = color
        )
    }
}

@Composable
fun RatingCountText(
    modifier: Modifier = Modifier,
    ratingCount: Int,
    color: Color = onSurfaceLight
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_person_24),
            contentDescription = null,
            tint = primaryLight
        )
        Spacer(Modifier.width(2.dp))
        Text(
            text = ratingCount.toString(),
            style = GGamingTypography.bodySmall,
            color = color
        )
    }
}

@Composable
fun MetacriticText(rating: Int){
    Row(verticalAlignment = Alignment.CenterVertically){
        Text(
            text = stringResource(R.string.metacritic),
            style = GGamingTypography.labelLarge,
            color = onSurfaceLight
        )
        Spacer(Modifier.width(4.dp))
        Text(
            modifier = Modifier.clip(RoundedCornerShape(4.dp))
                .background(errorContainerLight)
                .padding(4.dp),
            text = rating.toString(),
            style = GGamingTypography.titleMedium
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1F1A1F)
@Composable
fun RatingTextPreview(){
    RatingText(rating = 4.99)
}

@Preview(showBackground = true, backgroundColor = 0xFF1F1A1F)
@Composable
fun RatingCountTextPreview(){
    RatingCountText(ratingCount = 9000)
}

@Preview(showBackground = true, backgroundColor = 0xFF1F1A1F)
@Composable
fun MetacriticTextPreview(){
    MetacriticText( 92)
}