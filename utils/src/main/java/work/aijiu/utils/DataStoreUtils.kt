package work.aijiu.utils

import android.content.Context
import com.tencent.mmkv.MMKV

object DataStoreUtils {

    lateinit var dataStore: MMKV
    private val DEFAULT_MMKV_NAME = "default"

    fun init(context: Context) {
        val path = context.filesDir.absolutePath + "/onepieceAijiuMmkv"
        MMKV.initialize(context)
        dataStore = MMKV.mmkvWithID(DEFAULT_MMKV_NAME, MMKV.MULTI_PROCESS_MODE, "aijiu0316", path)
    }

    fun put(key: String, value: Any?) {
        when (value) {
            null -> dataStore.removeValueForKey(key)
            is Int -> dataStore.encode(key, value)
            is Long -> dataStore.encode(key, value)
            is Float -> dataStore.encode(key, value)
            is Double -> dataStore.encode(key, value)
            is Boolean -> dataStore.encode(key, value)
            is String -> dataStore.encode(key, value)
            is Set<*> -> dataStore.encode(key, value as Set<String>)
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    inline fun <reified T> get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            Int::class -> dataStore.decodeInt(key, defaultValue as? Int ?: 0) as T
            Long::class -> dataStore.decodeLong(key, defaultValue as? Long ?: 0L) as T
            Float::class -> dataStore.decodeFloat(key, defaultValue as? Float ?: 0F) as T
            Double::class -> dataStore.decodeDouble(key, defaultValue as? Double ?: 0.0) as T
            Boolean::class -> dataStore.decodeBool(key, defaultValue as? Boolean ?: false) as T
            String::class -> dataStore.decodeString(key, defaultValue as? String ?: "") as T
            Set::class -> dataStore.decodeStringSet(
                key,
                defaultValue as? Set<String> ?: setOf()
            ) as T
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    fun remove(key: String) {
        dataStore.removeValueForKey(key)
    }

    fun clearAll() {
        dataStore.clearAll()
    }
}