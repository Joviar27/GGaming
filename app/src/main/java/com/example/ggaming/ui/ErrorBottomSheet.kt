package com.example.ggaming.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ggaming.R
import com.example.ggaming.ui.theme.GGamingTypography
import com.example.ggaming.ui.theme.onSurfaceLight
import com.example.ggaming.ui.theme.surfaceContainerLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorBottomSheet(
    message: String,
    show: Boolean,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    
    if(show){
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onDismiss.invoke()
            }
        ) {
            ErrorBottomSheetContent(message)
        }
    }
}

@Composable
fun ErrorBottomSheetContent(message: String){
    Column(Modifier
        .fillMaxWidth()
        .background(surfaceContainerLight)
        .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.error_title),
            color = onSurfaceLight,
            style = GGamingTypography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.error_message, message),
            color = onSurfaceLight,
            style = GGamingTypography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorBottomSheetPreview(){
    ErrorBottomSheetContent("Example error message")
}