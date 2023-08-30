package work.aijiu.onepiece.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import org.greenrobot.eventbus.EventBus

enum class NetWorkType(val type: Int, val typeName: String) {
    NIL(0, "无网路"),
    WIFI(1, "WIFI"),
    Mobile(2, "移动网络")
}

class NetworkConnectChangedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val connManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            // 处理网络已连接的情况
        } else {
            // 处理网络已断开的情况
        }
        if (intent.action === ConnectivityManager.CONNECTIVITY_ACTION) {
            EventBus.getDefault().post(getNetInfo(context))
        }
    }
    companion object {
        private const val TAG = "NetworkChangedReceiver"
    }
}

data class NetWorkInfo(val isConnected: Boolean, val type: String)

fun getNetInfo(context: Context?): NetWorkInfo {
    val cm: ConnectivityManager =
        context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT < 23) {
        cm.activeNetworkInfo?.let {
            if (!it.isAvailable) return NetWorkInfo(false, NetWorkType.NIL.typeName)
            else return NetWorkInfo(false, it.typeName)
        } ?: return NetWorkInfo(false, NetWorkType.NIL.typeName)
    } else {
        cm.getNetworkCapabilities(cm.activeNetwork)?.let {
            when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return NetWorkInfo(
                    true,
                    NetWorkType.WIFI.typeName
                )
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return NetWorkInfo(
                    true,
                    NetWorkType.Mobile.typeName
                )
                else -> return NetWorkInfo(false, NetWorkType.NIL.typeName)
            }
        } ?: return NetWorkInfo(false, NetWorkType.NIL.typeName)
    }
}
