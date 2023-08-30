package work.aijiu.onepiece.ui.main

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import work.aijiu.onepiece.ui.main.nav.OnePieceActions
import work.aijiu.onepiece.ui.main.nav.OnePieceDestinations
import work.aijiu.onepiece.ui.page.changeTheme.ChangeThemePage
import work.aijiu.onepiece.ui.page.clockIn.ClockInPage


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    startDestination: String = OnePieceDestinations.HOME_PAGE_ROUTE,
) {
    val navController = rememberAnimatedNavController()
    val actions = remember(navController) { OnePieceActions(navController) }
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composableHorizontal(OnePieceDestinations.HOME_PAGE_ROUTE) {
            MainPage(actions)
        }
        composableHorizontal(OnePieceDestinations.CHANGE_THEME_ROUTE) {
            ChangeThemePage(actions)
        }
        composableHorizontal(OnePieceDestinations.TOOL_CLOCK_IN_ROUTE) {
            ClockInPage(actions)
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composableHorizontal(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    this@composableHorizontal.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        content = content,
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
    )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composableVertical(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    this@composableVertical.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(300)
            )
        },
        content = content,
    )
}