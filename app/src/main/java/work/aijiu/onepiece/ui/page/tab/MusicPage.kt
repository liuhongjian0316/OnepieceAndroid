package work.aijiu.onepiece.ui.page.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import work.aijiu.onepiece.ui.main.HomeViewModel
import work.aijiu.onepiece.ui.main.nav.OnePieceActions
import work.aijiu.onepiece.R


@Composable
fun MusicPage(
    modifier: Modifier,
    isLand: Boolean,
    homeViewModel: HomeViewModel,
    actions: OnePieceActions,
) {
    Column(modifier = modifier.fillMaxSize()) {
//        MusicPageTopBar(
//            title = "音乐",
//            rightImg = Icons.Filled.Headset,
//            rightClick = rightClick,
//        )

        MyScreenContent()
        ScrollableAppBar()
    }

}

val rightClick: () -> Unit = {
}

@Composable
fun ScrollableAppBar() {
    val lazyListState = rememberLazyListState()
    val visibility by remember {
        derivedStateOf {
            when {
                lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyListState.firstVisibleItemIndex == 0 -> {
                    val imageSize = lazyListState.layoutInfo.visibleItemsInfo[0].size
                    val scrollOffset = lazyListState.firstVisibleItemScrollOffset

                    scrollOffset / imageSize.toFloat()
                }
                else -> 1f
            }
        }
    }
    val firstItemTranslationY by remember {
        derivedStateOf {
            when {
                lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyListState.firstVisibleItemIndex == 0 -> lazyListState.firstVisibleItemScrollOffset * .6f
                else -> 0f
            }
        }
    }

    Box {
        LazyColumn(state = lazyListState) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Landscape in BW",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(100.dp)
                        .graphicsLayer {
                            alpha = 1f - visibility
                            translationY = firstItemTranslationY
                        }
                )
            }
            itemsIndexed(items = List(100) { "Item $it" }) { index, item ->
                Text(text = item, fontSize = 24.sp)
            }
        }

    }
}


@Composable
fun SwipeToRefreshLayout(
    refreshingState: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val state = rememberSwipeRefreshState(refreshingState)
    SwipeRefresh(state = state, onRefresh = onRefresh) {
        Box(
            Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(modifier: Modifier = Modifier) {
    var items by remember { mutableStateOf(List(30) { "Item $it" }) }
    SwipeToRefreshLayout(
        refreshingState = false,
        onRefresh = {
            // simulate a long refresh delay
            items = List(30) { "Refreshed Item $it" }
        }
    ) {
        LazyColumn(modifier = modifier.padding(16.dp)) {
            items(items = items) { item ->
                Text(text = item, modifier = Modifier.padding(4.dp))
            }
        }
    }
}


//@Composable
//fun MusicPageTopBar(
//    title: String,
//    rightImg: ImageVector = Icons.Rounded.MoreVert,
//    rightClick: (() -> Unit)? = null,
//) {
//    Column(modifier = Modifier.background(color = MaterialTheme.colors.primary)) {
//        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(43.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start,
//        ) {
//            Text(
//                modifier = Modifier
//                    .weight(1f)
//                    .wrapContentWidth(Alignment.CenterHorizontally),
//                text = title,
//                style = MaterialTheme.typography.subtitle1,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                color = MaterialTheme.colors.secondary
//            )
//            if (rightClick != null) {
//                IconButton(
//                    modifier = Modifier.wrapContentWidth(Alignment.End),
//                    onClick = rightClick
//                ) {
//                    Icon(
//                        imageVector = rightImg,
//                        contentDescription = "more",
//                        tint = MaterialTheme.colors.secondary
//                    )
//                }
//            }
//        }
//    }
//}


