package per.goweii.log

class LogPrintHandler {
    private val printers = mutableListOf<LogPrinter>()

    fun add(printer: LogPrinter) {
        printers.add(printer)
    }

    fun remove(printer: LogPrinter) {
        printers.remove(printer)
    }

    fun contains(printer: LogPrinter) {
        printers.contains(printer)
    }

    fun print(level: Log.Level, tag: String?, header: LogHeader?, msg: String?) {
        printers.forEach { it.print(level, tag, header, msg) }
    }
}