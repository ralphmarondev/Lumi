package com.ralphmarondev.launcher.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.domain.model.LumiApp

@Composable
fun AppContainer(
    lumiApp: LumiApp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showAppName: Boolean = false
) {
    Column(
        modifier = modifier.padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(onClick = onClick) {
            Column(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(lumiApp.icon),
                    contentDescription = lumiApp.name,
                    modifier = Modifier
                        .size(40.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        AnimatedVisibility(visible = showAppName) {
            Text(
                text = lumiApp.name,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 11.sp
            )
        }
    }
}