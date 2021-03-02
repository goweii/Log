package per.goweii.log

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

data class LogHeader(
    val timestamp: Long,
    val threadName: String,
    val fileName: String,
    val lineNumber: Int,
    val className: String,
    val methodName: String
) {
    val fileInfo = "($fileName:$lineNumber)"
    val classInfo = "$className.$methodName$fileInfo"
    val timeFormat = sdf.format(Date(timestamp))

    override fun toString(): String {
        return "$timeFormat$[$threadName]$classInfo"
    }

    companion object {
        private val sdf: SimpleDateFormat get() = sdfLocal.get()!!

        private val sdfLocal = object : ThreadLocal<SimpleDateFormat>() {
            @SuppressLint("SimpleDateFormat")
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            }
        }
    }
}