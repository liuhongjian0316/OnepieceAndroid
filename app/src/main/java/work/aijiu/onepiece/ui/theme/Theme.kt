package work.aijiu.onepiece.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import work.aijiu.utils.DataStoreUtils


const val CHANGED_THEME = "CHANGED_THEME"

// 天蓝色
const val SKY_BLUE_THEME = 0

// 灰色
const val GRAY_THEME = 1

// 深蓝色
const val DEEP_BLUE_THEME = 2

// 绿色
const val GREEN_THEME = 3

// 紫色
const val PURPLE_THEME = 4

// 橘黄色
const val ORANGE_THEME = 5

// 棕色
const val BROWN_THEME = 6

// 红色
const val RED_THEME = 7

// 青色
const val CYAN_THEME = 8

// 品红色
const val MAGENTA_THEME = 9

/**
 * 主题状态
 */
val themeTypeState: MutableState<Int?> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    mutableStateOf(getDefaultThemeId())
}

/**
 * 获取当前默认主题
 */
fun getDefaultThemeId(): Int? = DataStoreUtils.get(CHANGED_THEME, SKY_BLUE_THEME)

/**
 * 通过主题 ID 来获取需要的主题
 */
private fun getThemeForThemeId(themeId: Int?) = when (themeId) {
    SKY_BLUE_THEME -> {
        onePieceLightColors(
            primary = primaryLight
        )
    }
    GRAY_THEME -> {
        onePieceLightColors(
            primary = gray_theme
        )
    }
    DEEP_BLUE_THEME -> {
        onePieceDarkColors(
            primary = deep_blue_theme
        )
    }
    GREEN_THEME -> {
        onePieceLightColors(
            primary = green_theme
        )
    }
    PURPLE_THEME -> {
        onePieceLightColors(
            primary = purple_theme
        )
    }
    ORANGE_THEME -> {
        onePieceLightColors(
            primary = orange_theme
        )
    }
    BROWN_THEME -> {
        onePieceDarkColors(
            primary = brown_theme
        )
    }
    RED_THEME -> {
        onePieceDarkColors(
            primary = red_theme
        )

    }
    CYAN_THEME -> {
        onePieceLightColors(
            primary = cyan_theme
        )
    }
    MAGENTA_THEME -> {
        onePieceLightColors(
            primary = magenta_theme
        )
    }
    else -> {
        onePieceLightColors(
            primary = primaryLight
        )
    }
}


@Composable
fun OnePieceTheme(
    themeId: Int? = 0,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        onePieceDarkColors()
    } else {
        getThemeForThemeId(themeId)
    }
    androidx.compose.material.MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}



//private val DarkColorScheme = darkColorScheme(
//    primary = Color(0xFFBB86FC),
//    secondary = Color(0xFF03DAC6),
//    background = Color(0xFF121212),
//    surface = Color(0xFF121212),
//    onPrimary = Color.Black,
//    onSecondary = Color.Black,
//    onBackground = Color.White,
//    onSurface = Color.White,
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Color(0xFF6200EE),
//    secondary = Color(0xFF03DAC6),
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black,
//)


// 旧
//@Composable
//fun OnePieceTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
//            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
//        }
//    }
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}

