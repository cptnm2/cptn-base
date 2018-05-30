package cptn.util

import java.io.Serializable
import java.util.*

// 可包含多个数据的容器，常用作函数返回值
class ProcessResult : Serializable {
    var code: Int = 0
    var exception: Exception? = null

    private val msgs: MutableList<String>?
    private val datas: MutableMap<String, Any>

    val isOK: Boolean
        get() = code == CODE_OK

    /**
     * 返回第一条消息
     * @return
     */
    val firstMsg: String
        get() {
            if (msgs == null) {
                return ""
            }

            return if (msgs.size == 0) {
                ""
            } else msgs[0]

        }

    val defData: Any?
        get() = getData(DEFAULT_KEY)

    init {
        code = CODE_OK

        msgs = ArrayList()
        datas = HashMap()
    }

    fun setOK() {
        code = CODE_OK
    }

    fun setErr() {
        code = -1
    }

    fun setErr(code: Int) {
        this.code = code
    }

    fun setErr(code: Int, msg: String) {
        this.code = code
        this.addMsg(msg)
    }

    fun setErr(ex: Exception?) {
        setErr()

        if (ex != null) {
            this.exception = ex
            this.addMsg(ex.message)
        }
    }

    fun getMsgs(): List<String>? {
        return msgs
    }

    fun getDatas(): Map<String, Any> {
        return datas
    }

    fun addMsg(msg: String?) {
        if (msg == null) {
            return
        }

        msgs!!.add(msg)
    }

    fun putData(key: String, value: Any) {
        if (StrUtil.isEmpty2(key)) {
            return
        }

        datas[key] = value
    }

    fun getData(key: String): Any? {
        return if (StrUtil.isEmpty2(key)) {
            null
        } else datas[key]

    }

    fun putDefData(value: Any) {
        putData(DEFAULT_KEY, value)
    }

    fun clearData() {
        datas.clear()
    }

    fun clearMsg() {
        msgs!!.clear()
    }

    fun toJson(): String {
        return if (isOK) {
            String.format("{\"%s\":%d}", JK_CODE, 0)
        } else {
            String.format("{\"%s\":%d, \"%s\":\"%s\"}", JK_CODE, code, JK_MSG, firstMsg)
        }
    }

    companion object {
        private const val serialVersionUID = 1L

        val CODE_OK = 0
        val DEFAULT_KEY = "_def_key_"

        // 返回的json对象属性
        var JK_CODE = "code"
        var JK_MSG = "msg"
    }
}
