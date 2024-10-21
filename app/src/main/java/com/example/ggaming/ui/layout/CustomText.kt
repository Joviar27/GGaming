package com.example.ggaming.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.example.ggaming.R
import com.example.ggaming.ui.theme.GGamingTypography
import com.example.ggaming.ui.theme.errorContainerLight
import com.example.ggaming.ui.theme.onSurfaceLight
import com.example.ggaming.ui.theme.primaryLight

@Composable
fun RatingText(rating: Double, color: Color = onSurfaceLight){
    Row(
        verticalAlignment = Alignment.CenterVertically
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
fun RatingCountText(ratingCount: Int, color: Color = onSurfaceLight){
    Row(
        verticalAlignment = Alignment.CenterVertically
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
        Box(Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(errorContainerLight)
        ){
            Text(
                modifier = Modifier.padding(4.dp),
                text = rating.toString(),
                style = GGamingTypography.titleMedium
            )
        }
    }
}