package com.example.demoapp.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.demoapp.R
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(images: List<Int>, modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState(pageCount = { images.size })
    LaunchedEffect(key1 = true) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(pagerState.currentPage.plus(1) % images.size)
        }
    }
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp)
    ) { index ->
        Card(modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 8.dp)) {
            Image(
                painter = painterResource(id = images[index]),
                contentDescription = "slider image",
                modifier = Modifier.aspectRatio(3 / 1.8f),
                contentScale = ContentScale.Crop
            )
        }

    }


}


@Preview
@Composable
private fun ImageSlider_Preview() {
    ImageSlider(demoImages)
}

private val demoImages = listOf(
    R.drawable.image_1, R.drawable.image_2
)