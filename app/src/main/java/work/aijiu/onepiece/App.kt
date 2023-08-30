package work.aijiu.onepiece

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import dagger.hilt.android.HiltAndroidApp
import work.aijiu.onepiece.service.AlarmReceiver
import work.aijiu.utils.DataStoreUtils

@HiltAndroidApp
class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var appContext: Context? = null
    }


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        DataStoreUtils.init(this)
        setupAlarm(this)
    }

    private fun setupAlarm (context:Context) {
        val receiver = ComponentName(context, AlarmReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
//        context.packageManager.setComponentEnabledSetting(
//            receiver,
//            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//            PackageManager.DONT_KILL_APP
//        )
    }
}