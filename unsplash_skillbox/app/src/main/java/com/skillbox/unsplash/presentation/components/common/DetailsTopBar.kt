package com.skillbox.unsplash.presentation.components.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.skillbox.unsplash.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    modifier: Modifier = Modifier,
    parentName: String,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                text = parentName
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.body),
            navigationIconContentColor = colorResource(id = R.color.body)
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onShareClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share_photo),
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TopAppBarPreview() {
    MaterialTheme {
        DetailsTopBar(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            parentName = "Characters",
            onBackClick = {},
            onShareClick = {}
        )
    }
}