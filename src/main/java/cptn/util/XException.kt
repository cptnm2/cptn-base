package cptn.util

// 自定义异常
class XException : RuntimeException {
    var errCode: Int = 0

    constructor() : super() {}

    constructor(msg: String) : super(msg) {}

    constructor(err: Int, msg: String) : super(msg) {
        errCode = err
    }

    override fun toString(): String {
        return "$errCode: $message"
    }
}
