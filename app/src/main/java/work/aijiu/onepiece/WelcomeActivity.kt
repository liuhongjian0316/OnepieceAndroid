package work.aijiu.onepiece

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.WindowCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import work.aijiu.onepiece.componse.UpdateDialog
import work.aijiu.onepiece.model.AppVersion
import work.aijiu.onepiece.model.ResponseResult
import work.aijiu.onepiece.model.ResponseResultUtils
import work.aijiu.onepiece.network.BaseActivity
import work.aijiu.onepiece.network.NetWorkInfo
import work.aijiu.onepiece.network.OkHttpClientInstance
import java.io.IOException


@SuppressLint("StaticFieldLeak")


class WelcomeActivity : BaseActivity() {

    private lateinit var handler: Handler
    val UPDATE_MESSAGE = 0
    val START_APP_MESSAGE = 1
    private var message = ""
    var appVersionGlobal: AppVersion? = null
    private var updateDialog: UpdateDialog? = null
    private var onlyChangeOnce = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        updateDialog =
            UpdateDialog(this, R.layout.update_dialog_layout, intArrayOf(R.id.dialog_sure))
        handler = Handler(Looper.getMainLooper()) { p0 ->
            if (p0.what == START_APP_MESSAGE) {
                val intent = Intent()
                intent.setClass(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            if (p0.what == UPDATE_MESSAGE) {
                showUpdateDialog()
            }
            true
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEventThis(event: NetWorkInfo) {
        if (!onlyChangeOnce) {
            if (event.isConnected) {
                getAppVersion()
            } else {
                handler.sendEmptyMessageDelayed(START_APP_MESSAGE, 3000)
            }
            onlyChangeOnce = true
        }
    }

    private fun getAppVersion() {
        val client = OkHttpClientInstance.getClient()
        val request = Request.Builder()
            .url(BuildConfig.SERVER_URL + "/app/getAppVersion?appName=" + getString(R.string.app_name))
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.sendEmptyMessageDelayed(START_APP_MESSAGE, 3000)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val json = response.body?.string()
                    val responseResult: ResponseResult<AppVersion> =
                        Gson().fromJson(
                            json,
                            object : TypeToken<ResponseResult<AppVersion>>() {}.type
                        )
                    message = responseResult.message
                    if (ResponseResultUtils.isSuccess(responseResult.code)) {
                        val appVersion = responseResult.data
                        checkUpdate(appVersion)
                    } else {
                        handler.sendEmptyMessageDelayed(START_APP_MESSAGE, 3000)
                    }
                } catch (e: Exception) {
                    handler.sendEmptyMessageDelayed(START_APP_MESSAGE, 3000)
                }
            }
        })
    }

    private fun checkUpdate(appVersion: AppVersion?) {
        appVersionGlobal = appVersion
        if (appVersion != null && appVersion.versionCode!! > BuildConfig.VERSION_CODE) {
            handler.sendEmptyMessage(UPDATE_MESSAGE)
        } else {
            handler.sendEmptyMessageDelayed(START_APP_MESSAGE, 3000)
        }
    }

    private fun showUpdateDialog() {
        updateDialog!!.show()
        appVersionGlobal!!.versionName?.let { updateDialog!!.setVersionName(it) }
        appVersionGlobal!!.versionDesc?.let { updateDialog!!.setVersionDesc(it) }

        updateDialog!!.setOnCenterItemClickListener(object :
            UpdateDialog.OnCenterItemClickListener {
            override fun OnCenterItemClick(dialog: UpdateDialog?, view: View?) {
                if (view != null) {
                    when (view.id) {
                        R.id.dialog_sure -> {
                            val intent = Intent()
                            intent.action = "android.intent.action.VIEW"
                            val content_url: Uri = Uri.parse(appVersionGlobal!!.url)
                            intent.data = content_url
                            startActivity(intent)
                        }
                    }
                }
            }
        })
        updateDialog!!.setOnCancelListener(DialogInterface.OnCancelListener {
            handler.sendEmptyMessage(START_APP_MESSAGE)
        })

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (updateDialog != null) {
            updateDialog!!.dismiss();
        }
    }
}

