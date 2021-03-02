package per.goweii.log.header

import per.goweii.log.Log
import per.goweii.log.LogHeader
import per.goweii.log.LogHeaderBuilder

class DefaultLogHeaderBuilder : LogHeaderBuilder {
    private var invokeClass: Class<*>? = null
    private var bridgeCount: Int = 0

    fun setInvokeClass(invokeClass: Class<*>?) {
        this.invokeClass = invokeClass
    }

    fun setBridgeCount(bridgeCount: Int) {
        this.bridgeCount = bridgeCount
    }

    override fun build(): LogHeader {
        val thread = Thread.currentThread()
        val stackTrace = thread.stackTrace
        var invokeIndex = 0
        var foundLog = false
        for (index in 2 until stackTrace.size) {
            val stackTraceElement = stackTrace[index]
            if (stackTraceElement.className.startsWith(Log::class.java.name)) {
                foundLog = true
                invokeIndex = index
            } else {
                if (foundLog) {
                    break
                }
            }
        }
        var foundInvoke = false
        invokeClass?.let { invokeClass ->
            for (index in invokeIndex until stackTrace.size) {
                val stackTraceElement = stackTrace[index]
                if (stackTraceElement.className == invokeClass.name) {
                    foundInvoke = true
                    invokeIndex = index
                } else {
                    if (foundInvoke) {
                        break
                    }
                }
            }
        }
        var caller: StackTraceElement? = null
        val callerIndex = invokeIndex + 1
        if (callerIndex + bridgeCount in stackTrace.indices) {
            caller = stackTrace[callerIndex + bridgeCount]
        } else if (callerIndex in stackTrace.indices) {
            caller = stackTrace[callerIndex]
        }
        val timestamp = System.currentTimeMillis()
        val threadName = thread.name
        val fileName = caller?.fileName ?: "Unknown"
        val className = caller?.simpleClassName ?: "Unknown"
        val methodName = caller?.methodName ?: "unknown"
        val lineNumber = caller?.lineNumber ?: -1
        return LogHeader(timestamp, threadName, fileName, lineNumber, className, methodName)
    }

    private val StackTraceElement.simpleClassName: String
        get() {
            val i = className.lastIndexOf(".")
            val j = className.indexOf("$", i + 1)
            if (i >= j) return className.substring(i + 1)
            return className.substring(i + 1, j)
        }
}