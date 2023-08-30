package work.aijiu.onepiece.model

data class ResponseResult<T>(
    val code: Int,
    val message: String,
    val data: T?
)

object ResponseResultUtils {

    const val isOk: Int = 200

    public fun isSuccess(code: Int): Boolean {
        return isOk == code
    }
}
