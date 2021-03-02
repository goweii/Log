package per.goweii.log.printer.file

import com.tencent.mars.xlog.Xlog
import per.goweii.log.Log
import per.goweii.log.LogHeader
import per.goweii.log.LogPrinter

object FileLogPrinter : LogPrinter {
    private var opened = false

    init {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")
    }

    fun open(
        cachePath: String,
        logPath: String,
        namePrefix: String,
        publicKey: String
    ) {
        if (opened) return
        opened = true
        Xlog.appenderOpen(
            Xlog.LEVEL_ALL, Xlog.AppednerModeAsync,
            cachePath, logPath, namePrefix, 0, publicKey
        )
        Xlog.setConsoleLogOpen(false)
        com.tencent.mars.xlog.Log.setLogImp(Xlog())
    }

    fun close() {
        if (!opened) return
        com.tencent.mars.xlog.Log.appenderClose()
    }

    override fun print(level: Log.Level, tag: String?, header: LogHeader?, msg: String?) {
        if (!opened) return
        when (level) {
            Log.Level.ERROR -> com.tencent.mars.xlog.Log.e(
                formatTag(tag, header),
                formatMsg(header, msg)
            )
            Log.Level.WARN -> com.tencent.mars.xlog.Log.w(
                formatTag(tag, header),
                formatMsg(header, msg)
            )
            Log.Level.INFO -> com.tencent.mars.xlog.Log.i(
                formatTag(tag, header),
                formatMsg(header, msg)
            )
            Log.Level.DEBUG -> com.tencent.mars.xlog.Log.d(
                formatTag(tag, header),
                formatMsg(header, msg)
            )
            Log.Level.VERBOSE -> com.tencent.mars.xlog.Log.v(
                formatTag(tag, header),
                formatMsg(header, msg)
            )
            Log.Level.ASSERT -> com.tencent.mars.xlog.Log.f(
                formatTag(tag, header),
                formatMsg(header, msg)
            )
        }
    }

    private fun formatTag(tag: String?, header: LogHeader?): String {
        return tag ?: header?.className ?: "Unknown"
    }

    private fun formatMsg(header: LogHeader?, msg: String?): String {
        fun String.lined(): String {
            return if (contains("\n") || length > 20) {
                "\n$this"
            } else {
                ": $this"
            }
        }
        return when {
            header != null && msg != null -> "${header.classInfo}${msg.lined()}"
            header != null -> header.classInfo
            msg != null -> msg.lined()
            else -> ""
        }
    }
}