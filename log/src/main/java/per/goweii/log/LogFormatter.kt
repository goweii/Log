package per.goweii.log

interface LogFormatter {
    fun format(msg: Any, depth: Int, handler: LogFormatHandler): String?
}