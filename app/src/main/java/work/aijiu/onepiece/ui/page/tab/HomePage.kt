package work.aijiu.onepiece.ui.page.tab

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import work.aijiu.onepiece.ui.componse.LoadingContent
import work.aijiu.onepiece.ui.main.HomeViewModel
import work.aijiu.onepiece.ui.main.nav.OnePieceActions
import work.aijiu.onepiece.ui.theme.CHANGED_THEME
import work.aijiu.onepiece.ui.theme.SKY_BLUE_THEME
import work.aijiu.onepiece.ui.view.bar.OnePieceAppBar
import work.aijiu.utils.DataStoreUtils
import java.util.*

lateinit var textToSpeech: TextToSpeech

@Composable
fun HomePage(
    modifier: Modifier,
    isLand: Boolean,
    homeViewModel: HomeViewModel,
    actions: OnePieceActions,
) {
    val currentActivity = LocalContext.current
    Column(modifier = modifier.fillMaxSize()) {
        OnePieceAppBar("首页", false)
        initTTS(currentActivity)
        LoadingContent(modifier = modifier)
    }
}


fun initTTS(mContext: Context) {

// 初始化 TextToSpeech 实例
    textToSpeech = TextToSpeech(mContext) { status ->
        if (status == TextToSpeech.SUCCESS) {
            // 设置语言
            val result = textToSpeech.setLanguage(Locale.CHINA)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported")
            } else {
                textToSpeech.speak("测试语音播报", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        } else {
            // 初始化 TextToSpeech 失败，打印日志并退出
            Log.e("TTS", "Initialization failed")
        }
    }


//    textToSpeech = TextToSpeech(mContext) {
//        when (it) {
//            TextToSpeech.SUCCESS -> {
//                Log.e("调试信息", "成功")
//                speak("test")
//            }//成功
//            TextToSpeech.ERROR -> {
//                Log.e("调试信息", "Language not supported")
//            }//失败
//        }
//    }
//    //引擎列表
//    val engineList = textToSpeech.engines
//    //当前默认引擎
//    val engine = textToSpeech.defaultEngine
//    //语言
//    textToSpeech.language = Locale.CHINESE
//    //语调 越大越高
//    textToSpeech.setPitch(0.9f)
//    //语速 越大越快
//    textToSpeech.setSpeechRate(1f)
}

fun speak(text: String) {
    val result = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
}