package per.goweii.android.log

import per.goweii.log.Log
import per.goweii.log.LogHeader

data class LogMsg (
    val level: Log.Level,
    val tag: String?,
    val header: LogHeader?,
    val msg: String?
)