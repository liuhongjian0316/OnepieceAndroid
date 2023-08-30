package work.aijiu.onepiece.ui.main.nav

import androidx.navigation.NavHostController

/**
 * 对应用程序中的导航操作进行建模。
 */
class OnePieceActions(navController: NavHostController) {

    //    val enterArticle: (ArticleModel) -> Unit = { article ->
//        article.desc = ""
//        article.title = getHtmlText(article.title)
//        val gson = Gson().toJson(article).trim()
//        val result = URLEncoder.encode(gson, "utf-8")
//        navController.navigate("${PlayDestinations.ARTICLE_ROUTE}/$result")
//    }
//
//    val toSystemArticleList: (Int, String) -> Unit = { cid, name ->
//        navController.navigate("${PlayDestinations.SYSTEM_ARTICLE_LIST_ROUTE}/$cid/$name")
//    }
//
//    val toLogin: () -> Unit = {
//        navController.navigate(PlayDestinations.LOGIN_ROUTE)
//    }
//
    val toTheme: () -> Unit = {
        navController.navigate(OnePieceDestinations.CHANGE_THEME_ROUTE)
    }
    val toToolClockIn: () -> Unit = {
        navController.navigate(OnePieceDestinations.TOOL_CLOCK_IN_ROUTE)
    }
//
//    val toSearch: () -> Unit = {
//        navController.navigate(PlayDestinations.SEARCH_PAGE_ROUTE)
//    }

    val upPress: () -> Unit = {
        navController.navigateUp()
    }

}