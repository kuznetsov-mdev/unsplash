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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.skillbox.unsplash.domain.model.detail.ExifModel
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import com.skillbox.unsplash.domain.model.detail.StatisticModel
import com.skillbox.unsplash.presentation.components.RowWithWrap
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
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .statusBarsPadding()
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
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
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
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, end = 10.dp)
    ) {
        item {
            BottomImagePanel(author = imageDetail.author)
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            About(imageDetail.author.biography)
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            CameraParams(
                exif = imageDetail.exif,
                width = imageDetail.width,
                height = imageDetail.height
            )
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            Statistic(statisticModel = imageDetail.statistic)
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            TagPanel(tags = imageDetail.tags)
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

@Composable
fun About(description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
    ) {
        Text(text = "About:")
        Text(text = description)
    }
}

@Composable
fun CameraParams(
    exif: ExifModel,
    width: Int,
    height: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Parameter(label = "Camera", textValue = exif.make)
            Parameter(label = "Focal length", textValue = exif.focalLength)
            Parameter(label = "ISO", textValue = exif.iso.toString())
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Parameter(label = "Aperture", textValue = exif.aperture)
            Parameter(label = "Shutter speed", textValue = exif.exposureTime)
            Parameter(label = "Dimension", textValue = "$width x $height")
        }
    }
}

@Composable
fun Statistic(
    statisticModel: StatisticModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Parameter(modifier = Modifier.padding(top = 10.dp), label = "Views", textValue = statisticModel.views.toString())
        Parameter(modifier = Modifier.padding(top = 10.dp), label = "Downloads", textValue = statisticModel.downloads.toString())
        Parameter(modifier = Modifier.padding(top = 10.dp), label = "Likes", textValue = statisticModel.likes.toString())
    }
}

@Composable
fun Parameter(
    modifier: Modifier = Modifier,
    label: String,
    textValue: String
) {
    Column(
        modifier = modifier.padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
                .copy(
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
        )
        Text(text = textValue)
    }
}

@Composable
fun TagPanel(
    tags: List<String>
) {
    RowWithWrap() {
        tags.forEach {
            Tag(tagName = it)
        }
    }
}

@Composable
fun Tag(
    tagName: String
) {
    Row(
        modifier = Modifier
            .padding(start = 5.dp, top = 5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color = Color.LightGray)

    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    end = 5.dp,
                    top = 3.dp,
                    bottom = 3.dp
                ),
            softWrap = false,
            text = tagName
        )
    }

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageDetailScreenPreview() {
    MaterialTheme {
        ImageDetailScreen(
            imageDetailStateFlow = MockData.getImageDetail(),
            onRepeatClick = {},
            navigateUp = {}
        )
    }
}