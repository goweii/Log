package per.goweii.log

object Log {
    enum class Level(val value: Int) {
        ASSERT(1 shl 0),
        ERROR(1 shl 1),
        WARN(1 shl 2),
        INFO(1 shl 3),
        DEBUG(1 shl 4),
        VERBOSE(1 shl 5),
        ;

        companion object {
            val VERBOSE_ABOVE: Array<Level>
                get() = values().filter { it.value <= VERBOSE.value }.toTypedArray()
            val DEBUG_ABOVE: Array<Level>
                get() = values().filter { it.value <= DEBUG.value }.toTypedArray()
            val INFO_ABOVE: Array<Level>
                get() = values().filter { it.value <= INFO.value }.toTypedArray()
            val WARN_ABOVE: Array<Level>
                get() = values().filter { it.value <= WARN.value }.toTypedArray()
            val ERROR_ABOVE: Array<Level>
                get() = values().filter { it.value <= ERROR.value }.toTypedArray()
        }
    }

    private var levelFilter = 0
    private val printHandler = LogPrintHandler()

    fun setLevel(vararg levels: Level): Int {
        levelFilter = 0
        levels.forEach { levelFilter = levelFilter or it.value }
        return levelFilter
    }

    fun addLevel(vararg levels: Level): Int {
        levels.forEach { levelFilter = levelFilter or it.value }
        return levelFilter
    }

    fun removeLevel(vararg levels: Level): Int {
        levels.forEach { levelFilter = levelFilter xor it.value }
        return levelFilter
    }

    fun containLevel(level: Level): Boolean {
        return level.value and levelFilter != 0
    }

    fun addPrinter(printer: LogPrinter) {
        printHandler.add(printer)
    }

    fun removePrinter(printer: LogPrinter) {
        printHandler.remove(printer)
    }

    fun getPrintHandler(): LogPrintHandler {
        return printHandler
    }
}