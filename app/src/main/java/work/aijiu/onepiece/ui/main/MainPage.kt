package work.aijiu.onepiece.ui.main

import android.app.Activity
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import work.aijiu.onepiece.page.MinePage
import work.aijiu.onepiece.page.ToolPage
import work.aijiu.onepiece.ui.main.nav.BottomTabs
import work.aijiu.onepiece.ui.main.nav.OnePieceActions
import work.aijiu.onepiece.ui.page.tab.HomePage
import work.aijiu.onepiece.ui.page.tab.MusicPage
import java.util.*

@Composable
fun MainPage(actions: OnePieceActions) {
    val currentActivity = LocalContext.current as? Activity
    val viewModel: HomeViewModel = hiltViewModel()
    val position by viewModel.position.observeAsState()


    MainPageContent(viewModel, actions, position) { tab ->
        viewModel.onPositionChanged(tab)
    }
    BackHandler(enabled = true) {
        // 不清除后台
        currentActivity?.moveTaskToBack(true)
    }
}

@Composable
fun MainPageContent(
    homeViewModel: HomeViewModel,
    actions: OnePieceActions, position: BottomTabs?,
    onPositionChanged: (BottomTabs) -> Unit,
) {
    val tabs = BottomTabs.values()
    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        bottomBar = {
            BottomNavigation {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        modifier = Modifier.background(MaterialTheme.colors.primary),
                        icon = {
                            val painter: Painter = if (tab == position) {
                                rememberVectorPainter(tab.selectIcon)
                            } else {
                                rememberVectorPainter(tab.icon)
                            }
                            Icon(painter, contentDescription = null)
                        },
                        label = { Text(stringResource(tab.title).uppercase(Locale.ROOT)) },
                        selected = tab == position,
                        onClick = {
                            onPositionChanged(tab)
                        },
                        alwaysShowLabel = true,
                        selectedContentColor = Color(255, 255, 255),
                        unselectedContentColor = when (isSystemInDarkTheme()) {
                            true -> Color(255, 255, 255, 153)
                            false -> Color(0, 0, 0)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        // 当前是否为横屏
        val isLand = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
        when (position) {
            BottomTabs.TAB_FIRST_PAGE -> {
                HomePage(modifier, isLand, homeViewModel, actions)
            }
            BottomTabs.TAB_SECOND_PAGE -> {
                MusicPage(modifier, isLand, homeViewModel, actions)
            }
            BottomTabs.TAB_THIRD_PAGE -> {
            }
            BottomTabs.TAB_FORTH_PAGE -> {
                ToolPage(modifier, isLand, homeViewModel, actions)
            }
            BottomTabs.TAB_FIFTH_PAGE -> {
                MinePage(modifier, isLand, homeViewModel, actions)
            }
            else -> {
//                XLog.e("The page display is faulty")
                throw IllegalAccessException()
            }
        }
    }
}