package per.goweii.log

interface LogPrinter {
    fun print(level: Log.Level, tag: String?, header: LogHeader?, msg: String?)
}