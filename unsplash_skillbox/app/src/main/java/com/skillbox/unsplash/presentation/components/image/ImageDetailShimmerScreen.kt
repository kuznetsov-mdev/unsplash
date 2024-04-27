package com.skillbox.unsplash.presentation.components.image

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skillbox.unsplash.R
import com.skillbox.unsplash.presentation.components.common.shimmerEffect

@Composable
fun ImageDetailShimmerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        //image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 220.dp)
                .shimmerEffect()
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 10.dp)
        ) {
            //avatar
            Box(
                modifier = Modifier
                    .size(size = 50.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 120.dp, height = 14.dp)
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 14.dp)
                        .shimmerEffect()
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .height(2.dp)
                .background(color = colorResource(R.color.shimmer))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(14.dp)
                .padding(start = 10.dp)
                .shimmerEffect()
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .height(2.dp)
                .background(color = colorResource(R.color.shimmer))
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CameraParamsShimmerContent()
            CameraParamsShimmerContent()
        }

        ImageStatisticShimmer()
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        TagsLayoutShimmer()
    }
}

@Composable
fun CameraParamsShimmerContent() {
    Column(
        modifier = Modifier.width(200.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat((0..2).count()) {
            CameraParameterShimmer()
        }
    }
}

@Composable
fun CameraParameterShimmer() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
        )
        Box(
            modifier = Modifier
                .size(width = 120.dp, height = 14.dp)
                .shimmerEffect()
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 14.dp)
                .shimmerEffect()
        )
    }
}

@Composable
fun ImageStatisticShimmer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .height(2.dp)
                .background(color = colorResource(R.color.shimmer))
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatisticItemShimmer()
            StatisticItemShimmer()
            StatisticItemShimmer()
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .height(2.dp)
                .background(color = colorResource(R.color.shimmer))
        )
    }
}

@Composable
fun StatisticItemShimmer() {
    Column(
        modifier = Modifier.width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 70.dp, height = 12.dp)
                .shimmerEffect()
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
        Box(
            modifier = Modifier
                .size(width = 40.dp, height = 12.dp)
                .shimmerEffect()
        )
    }
}

@Composable
fun TagsLayoutShimmer() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat((0..4).count()) {
            TagShimmer()
        }
    }
}

@Composable
fun TagShimmer() {
    Box(
        modifier = Modifier
            .size(width = 60.dp, height = 20.dp)
            .shimmerEffect()
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun IImageDetailShimmerScreenPreview() {
    MaterialTheme {
        ImageDetailShimmerScreen()
    }
}