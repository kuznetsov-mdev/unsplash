package com.skillbox.unsplash.presentation.components.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.skillbox.unsplash.R
import com.skillbox.unsplash.presentation.utils.MockData

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageSize: Int,
    upText: String,
    upTextSize: Int = imageSize,
    downText: String,
    downTextSize: Int = imageSize
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(imageSize.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .build(),
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_close),
            placeholder = painterResource(id = R.drawable.user_icon_place_holder)
        )
        Column(
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    top = 5.dp,
                    bottom = 5.dp,
                    end = 10.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            ImageLabel(
                text = upText,
                textSize = if (upTextSize == imageSize) upTextSize / 3 else upTextSize
            )
            ImageLabel(
                text = downText,
                textSize = if (downTextSize == imageSize) imageSize / 4 else downTextSize
            )
        }
    }
}

@Composable
fun ImageLabel(
    text: String,
    textSize: Int
) {

    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium
            .copy(
                fontSize = (textSize.sp)
            ),
        color = colorResource(id = R.color.body)
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AvatarPreview() {
    MaterialTheme {
        Avatar(
            imageUrl = MockData.getUserImage().avatarUrl,
            upText = "Nick Wilkerson",
            downText = "Unsplashplus",
            imageSize = 60,
            downTextSize = 14
        )
    }
}