package work.aijiu.onepiece.page

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.*
import work.aijiu.onepiece.config.RouteConfig


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainPage(navHostController: NavHostController) {
    val currentActivity = LocalContext.current as? Activity
    val statusBarHeight = LocalContext.current.resources.getDimensionPixelSize(
        LocalContext.current.resources.getIdentifier("status_bar_height", "dimen", "android")
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IndexTitleBar(statusBarHeight)
        Text(text = "首页")
        Button(onClick = {
//            navHostController.navigate(RouteConfig.FIRST_PAGE)
            navHostController.navigate(RouteConfig.FIRST_PAGE) {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = false
                }
                launchSingleTop = true
                restoreState = false
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text("跳转FirstPage(必传参数)")
        }
    }
    BackHandler(enabled = true) {
        currentActivity?.moveTaskToBack(true)
    }
}

@Composable
fun IndexTitleBar(statusBarHeight: Int) {
    Row(
        modifier = Modifier
            .height(statusBarHeight.dp)
            .fillMaxWidth()
            .background(Color.Magenta)
    ) {

    }
}




