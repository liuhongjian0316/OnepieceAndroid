package work.aijiu.onepiece.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object OkHttpClientInstance {
    private var client: OkHttpClient? = null

    fun getClient(): OkHttpClient {
        if (client == null) {
            val interceptor = Interceptor { chain ->
                val original = chain.request()
                // 添加 Token 到请求头
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer ${getToken()}")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }
        return client!!
    }
}

fun getToken(): String {
    // 后续编写获取token
    return "token"
}
