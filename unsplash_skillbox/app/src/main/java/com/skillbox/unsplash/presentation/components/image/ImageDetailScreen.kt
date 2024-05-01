package com.skillbox.unsplash.presentation.components.image

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.skillbox.unsplash.common.LoadState
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import com.skillbox.unsplash.presentation.components.common.DetailsTopBar
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ImageDetailScreen(
    modifier: Modifier = Modifier,
    imageDetailStateFlow: StateFlow<LoadState<ImageDetailModel>>,
    onRepeatClick: () -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    val imageDetailLoadState by imageDetailStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {

        when (imageDetailLoadState) {
            is LoadState.Loading -> ImageDetailShimmerScreen()
            is LoadState.Error -> Text(text = "Error")
            is LoadState.Success -> ImageDetail(
                onRepeatClick = { },
                navigateUp = { }
            )
        }

    }

}


@Composable
fun ImageDetail(
    onRepeatClick: () -> Unit,
    navigateUp: () -> Unit
) {
    DetailsTopBar(
        parentName = "Images",
        onBackClick = { navigateUp() },
        onShareClick = {}
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageDetailScreenPreview() {
    MaterialTheme {
//        ImageDetailScreen()
    }
}