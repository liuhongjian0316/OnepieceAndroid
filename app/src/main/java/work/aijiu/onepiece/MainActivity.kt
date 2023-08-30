package work.aijiu.onepiece

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.*
import dagger.hilt.android.AndroidEntryPoint
import work.aijiu.onepiece.config.RouteConfig
import work.aijiu.onepiece.network.BaseActivity
import work.aijiu.onepiece.page.*
import work.aijiu.onepiece.ui.main.NavGraph
import work.aijiu.onepiece.ui.theme.GrayAppAdapter
import work.aijiu.onepiece.ui.theme.OnePieceTheme
import work.aijiu.onepiece.ui.theme.themeTypeState
import work.aijiu.utils.setAndroidNativeLightStatusBar
import work.aijiu.utils.transparentStatusBar


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setAndroidNativeLightStatusBar()

        setContent {
            OnePieceTheme(themeTypeState.value) {
                GrayAppAdapter {
                    NavGraph()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 销毁资源
    }
}


