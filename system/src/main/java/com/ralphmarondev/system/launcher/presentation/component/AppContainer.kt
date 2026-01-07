package com.ralphmarondev.system.launcher.presentation.component

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
import com.ralphmarondev.system.launcher.domain.model.MiniApp

@Composable
fun AppContainer(
    info: MiniApp,
    modifier: Modifier = Modifier,
    showAppName: Boolean = false
) {
    Column(
        modifier = modifier.padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = info.onClick,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(info.image),
                    contentDescription = info.name,
                    modifier = Modifier
                        .size(40.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        AnimatedVisibility(visible = showAppName) {
            Text(
                text = info.name,
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