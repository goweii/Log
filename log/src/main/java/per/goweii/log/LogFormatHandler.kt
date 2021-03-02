package per.goweii.log

import java.util.concurrent.CopyOnWriteArrayList

class LogFormatHandler {
    private val formatters = CopyOnWriteArrayList<LogFormatter>()

    var depthPrefix = "  "
        private set

    fun register(formatter: LogFormatter) {
        formatters.add(formatter)
    }

    fun unregister(formatter: LogFormatter) {
        formatters.remove(formatter)
    }

    fun format(obj: Any?, depth: Int): String? {
        obj ?: return null
        formatters.forEach { formatter ->
            try {
                formatter.format(obj, depth, this)?.let { return it }
            } catch (e: Throwable) {
            }
        }
        return obj.toString()
    }
}