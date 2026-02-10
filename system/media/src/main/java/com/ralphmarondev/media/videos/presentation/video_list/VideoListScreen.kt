package com.ralphmarondev.media.videos.presentation.video_list

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListScreenRoot(
    navigateBack: () -> Unit,
    playVideo: (String) -> Unit
) {
    val viewModel: VideoListViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.onAction(VideoListAction.LoadVideos)
    }

    LaunchedEffect(state.playVideo) {
        if (state.playVideo && !state.selectedVideoPath.isNullOrBlank()) {
            playVideo(state.selectedVideoPath)
            viewModel.onAction(VideoListAction.ResetNavigation)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Videos") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        if (state.videos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No videos found",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.videos) { video ->
                    VideoListItemCard(
                        video = video,
                        onClick = { viewModel.onAction(VideoListAction.PlayVideo(video)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
private fun VideoListItemCard(
    video: VideoItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val thumbnail = remember(video.file) {
        getVideoThumbnail(video.file)
    }

    OutlinedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(height = 68.dp, width = 114.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                if (thumbnail != null) {
                    Image(
                        painter = BitmapPainter(thumbnail.asImageBitmap()),
                        contentDescription = "Video thumbnail",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "No preview",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = video.name,
                    style = MaterialTheme.typography.titleMedium
                        .copy(color = MaterialTheme.colorScheme.secondary),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = DateFormat.format("dd MMM yyyy HH:mm", Date(video.dateCaptured))
                        .toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun getVideoThumbnail(file: File): Bitmap? {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(file.absolutePath)
        // capture frame at 1st second (1000000 microseconds)
        retriever.getFrameAtTime(1_000_000)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}
