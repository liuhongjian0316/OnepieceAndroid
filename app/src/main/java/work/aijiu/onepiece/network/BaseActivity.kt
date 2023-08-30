package work.aijiu.onepiece.network

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.Nullable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import work.aijiu.onepiece.R


abstract class BaseActivity : ComponentActivity() {
    protected var mContext: Context? = null
    private val TAG = "BaseActivity"
    protected var mNetConnected /*网络连接的状态，true表示有网络，flase表示无网络连接*/ = false
    private var mNetWorkChangReceiver /*网络状态变化的广播接收器*/: NetworkConnectChangedReceiver? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        EventBus.getDefault().register(this)
        //注册网络状态监听广播
        mNetWorkChangReceiver = NetworkConnectChangedReceiver()
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(mNetWorkChangReceiver, filter)
    }

    override fun onResume() {
        super.onResume()
        netStateChangedUI(getNetInfo(mContext).isConnected)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mNetWorkChangReceiver)
        EventBus.getDefault().unregister(this)
    }

    /**
     * 网络状态发生变化时的处理
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetWorkInfo) {
        Log.d(TAG,"网络发生变化${event.isConnected}-${event.type}")
        mNetConnected = event.isConnected
        netStateChangedUI(event.isConnected)
    }

    /**
     * 根据网络状态显示或者隐藏提示对话框
     * @param isConnected
     */
    private fun netStateChangedUI(isConnected: Boolean) {
        if (!isConnected) {
            Toast.makeText(mContext, mContext!!.getText(R.string.no_network), Toast.LENGTH_LONG)
                .show();
        }
    }

}