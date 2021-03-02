package per.goweii.log.printer.android

import per.goweii.log.Log
import per.goweii.log.LogHeader
import per.goweii.log.LogPrinter

object AndroidLogPrinter : LogPrinter {
    private var perLogMaxLength: Int = 4 * 1024

    fun setPerLogMaxLength(perLogMaxLength: Int) {
        this.perLogMaxLength = perLogMaxLength
    }

    override fun print(level: Log.Level, tag: String?, header: LogHeader?, msg: String?) {
        if (msg == null || msg.length < perLogMaxLength) {
            println(level, tag, header, msg)
            return
        }
        for (i in 0..msg.length / perLogMaxLength) {
            val start = i * perLogMaxLength
            var end = (i + 1) * perLogMaxLength
            end = if (end > msg.length) msg.length else end
            println(level, tag, header, msg.substring(start, end))
        }
    }

    private fun println(level: Log.Level, tag: String?, header: LogHeader?, msg: String?) {
        android.util.Log.println(
            getLevel(level),
            formatTag(tag, header),
            formatMsg(header, msg)
        )
    }

    private fun getLevel(level: Log.Level): Int {
        return when (level) {
            Log.Level.ERROR -> android.util.Log.ERROR
            Log.Level.WARN -> android.util.Log.WARN
            Log.Level.INFO -> android.util.Log.INFO
            Log.Level.DEBUG -> android.util.Log.DEBUG
            Log.Level.VERBOSE -> android.util.Log.VERBOSE
            Log.Level.ASSERT -> android.util.Log.ASSERT
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