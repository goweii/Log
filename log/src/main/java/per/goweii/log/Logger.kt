package per.goweii.log

class Logger {
    private var filter: Int = 0
    private val printHandler = LogPrintHandler()
    private val formatHandler = LogFormatHandler()
    private var headerBuilder: LogHeaderBuilder? = null

    fun setLevel(vararg levels: Log.Level) {
        filter = 0
        levels.forEach { filter = filter or it.value }
    }

    fun addLevel(vararg levels: Log.Level) {
        levels.forEach { filter = filter or it.value }
    }

    fun containLevel(level: Log.Level): Boolean {
        return level.value and filter != 0
    }

    fun removeLevel(vararg levels: Log.Level) {
        levels.forEach { filter = filter xor it.value }
    }

    fun addPrinter(printer: LogPrinter) {
        printHandler.add(printer)
    }

    fun removePrinter(printer: LogPrinter) {
        printHandler.remove(printer)
    }

    fun registerFormatter(formatter: LogFormatter) {
        formatHandler.register(formatter)
    }

    fun unregisterFormatter(formatter: LogFormatter) {
        formatHandler.unregister(formatter)
    }

    fun setHeaderBuilder(builder: LogHeaderBuilder?) {
        headerBuilder = builder
    }

    inline fun v(tag: String? = null, msg: () -> Any?) {
        log(Log.Level.VERBOSE, tag, msg)
    }

    inline fun d(tag: String? = null, msg: () -> Any?) {
        log(Log.Level.DEBUG, tag, msg)
    }

    inline fun i(tag: String? = null, msg: () -> Any?) {
        log(Log.Level.INFO, tag, msg)
    }

    inline fun w(tag: String? = null, msg: () -> Any?) {
        log(Log.Level.WARN, tag, msg)
    }

    inline fun e(tag: String? = null, msg: () -> Any?) {
        log(Log.Level.ERROR, tag, msg)
    }

    inline fun a(tag: String? = null, msg: () -> Any?) {
        log(Log.Level.ASSERT, tag, msg)
    }

    inline fun log(level: Log.Level, tag: String? = null, msg: () -> Any?) {
        if (Log.containLevel(level) && containLevel(level)) {
            print(level, tag, msg.invoke())
        }
    }

    fun v(tag: String? = null, msg: Any?) {
        log(Log.Level.VERBOSE, tag, msg)
    }

    fun d(tag: String? = null, msg: Any?) {
        log(Log.Level.DEBUG, tag, msg)
    }

    fun i(tag: String? = null, msg: Any?) {
        log(Log.Level.INFO, tag, msg)
    }

    fun w(tag: String? = null, msg: Any?) {
        log(Log.Level.WARN, tag, msg)
    }

    fun e(tag: String? = null, msg: Any?) {
        log(Log.Level.ERROR, tag, msg)
    }

    fun a(tag: String? = null, msg: Any?) {
        log(Log.Level.ASSERT, tag, msg)
    }

    fun log(level: Log.Level, tag: String? = null, msg: Any?) {
        if (Log.containLevel(level) && containLevel(level)) {
            print(level, tag, msg)
        }
    }

    fun print(level: Log.Level, tag: String?, msg: Any?) {
        print(level, tag, headerBuilder?.build(), formatHandler.format(msg, 0))
    }

    private fun print(level: Log.Level, tag: String?, header: LogHeader?, msg: String?) {
        Log.getPrintHandler().print(level, tag, header, msg)
        printHandler.print(level, tag, header, msg)
    }
}