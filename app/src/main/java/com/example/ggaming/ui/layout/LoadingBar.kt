package com.example.ggaming.ui.layout

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ggaming.ui.theme.surfaceContainerHighLight
import com.example.ggaming.ui.theme.tertiaryLight

@Composable
fun BoxScope.LoadingBar(){
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp)
            .align(Alignment.Center),
        color = tertiaryLight,
        trackColor = surfaceContainerHighLight
    )
}