package work.aijiu.onepiece.page

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


@Composable
fun ReadPage() {
    val currentActivity = LocalContext.current as? Activity
    val statusBarHeight = LocalContext.current.resources.getDimensionPixelSize(
        LocalContext.current.resources.getIdentifier("status_bar_height", "dimen", "android")
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IndexTitleBar(statusBarHeight)
        Text(text = "阅读")
        Button(onClick = {
//            NavUtil.get()
//                .navigation(RouteConfig.FIRST_PAGE, params = hashMapOf<String, String>().apply {
//                    put("key1", "传递参数key1")
//                    put("key2", "传递参数key2")
//                })
        }, modifier = Modifier.fillMaxWidth()) {
            Text("跳转FirstPage(必传参数)")
        }
    }
}




