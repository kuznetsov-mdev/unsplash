package com.skillbox.unsplash.presentation.components.image

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.skillbox.unsplash.R
import com.skillbox.unsplash.common.LoadState
import com.skillbox.unsplash.domain.model.UserModel
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import com.skillbox.unsplash.presentation.components.common.Avatar
import com.skillbox.unsplash.presentation.components.common.DetailsTopBar
import com.skillbox.unsplash.presentation.utils.MockData
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ImageDetailScreen(
    modifier: Modifier = Modifier,
    imageDetailStateFlow: StateFlow<LoadState<ImageDetailModel>>,
    onRepeatClick: () -> Unit,
    navigateUp: () -> Unit
) {


    val imageDetailLoadState by imageDetailStateFlow.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {

        when (imageDetailLoadState) {
            is LoadState.Loading -> ImageDetailShimmerScreen()
            is LoadState.Error -> Text(text = "Error")
            is LoadState.Success -> ImageDetail(
                imageDetail = (imageDetailLoadState as LoadState.Success<ImageDetailModel>).result,
                onRepeatClick = { },
                navigateUp = { }
            )
        }
    }

}


@Composable
fun ImageDetail(
    imageDetail: ImageDetailModel,
    onRepeatClick: () -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    DetailsTopBar(
        parentName = "Images",
        onBackClick = { navigateUp() },
        onShareClick = {}
    )
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(6.dp)
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(context).data(imageDetail.image.url).build(),
        contentDescription = null,
        error = painterResource(id = R.drawable.ic_network_error),
        placeholder = painterResource(id = R.drawable.image_placeholder),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, end = 10.dp)
    ) {
        item {
            BottomImagePanel(author = imageDetail.author)
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun BottomImagePanel(
    author: UserModel
) {
    Row(
        modifier = Modifier.padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Avatar(
            imageUrl = author.avatarUrl,
            imageSize = 50,
            upText = author.name,
            downText = author.nickname,
            downTextSize = 15
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_download_black),
                    contentDescription = null
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.favorite_empty),
                    contentDescription = null,
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageDetailScreenPreview() {
    MaterialTheme {
        ImageDetailScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            imageDetailStateFlow = MockData.getImageDetail(),
            onRepeatClick = {},
            navigateUp = {}
        )
    }
}