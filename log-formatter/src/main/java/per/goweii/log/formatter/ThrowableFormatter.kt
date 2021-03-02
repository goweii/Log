package per.goweii.log.formatter

import per.goweii.log.LogFormatHandler
import per.goweii.log.LogFormatter
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

class ThrowableFormatter : LogFormatter {
    override fun format(msg: Any, depth: Int, handler: LogFormatHandler): String? {
        val throwable = msg as? Throwable ?: return null
        var t: Throwable? = throwable
        val throwableList = ArrayList<Throwable>()
        while (t != null && !throwableList.contains(t)) {
            throwableList.add(t)
            t = t.cause
        }
        val size = throwableList.size
        val frames = ArrayList<String>()
        var nextTrace = getStackFrameList(throwableList[size - 1])
        var i = size
        while (--i >= 0) {
            val trace = nextTrace
            if (i != 0) {
                nextTrace = getStackFrameList(throwableList[i - 1])
                removeCommonFrames(trace, nextTrace)
            }
            if (i == size - 1) {
                frames.add(throwableList[i].toString())
            } else {
                frames.add(" Caused by: " + throwableList[i].toString())
            }
            frames.addAll(trace)
        }
        val sb = StringBuilder()
        for (element in frames) {
            sb.append(element).append("\n")
        }
        return sb.toString()
    }

    private fun getStackFrameList(throwable: Throwable): List<String> {
        val sw = StringWriter()
        val pw = PrintWriter(sw, true)
        throwable.printStackTrace(pw)
        val stackTrace = sw.toString()
        val frames = StringTokenizer(stackTrace, "\n")
        val list = ArrayList<String>()
        var traceStarted = false
        while (frames.hasMoreTokens()) {
            val token = frames.nextToken()
            val at = token.indexOf("at")
            if (at != -1 && token.substring(0, at).trim { it <= ' ' }.isEmpty()) {
                traceStarted = true
                list.add(token)
            } else if (traceStarted) {
                break
            }
        }
        return list
    }

    private fun removeCommonFrames(causeFrames: List<String>, wrapperFrames: List<String>) {
        var causeFrameIndex = causeFrames.size - 1
        var wrapperFrameIndex = wrapperFrames.size - 1
        while (causeFrameIndex >= 0 && wrapperFrameIndex >= 0) {
            val causeFrame = causeFrames[causeFrameIndex]
            val wrapperFrame = wrapperFrames[wrapperFrameIndex]
            if (causeFrame == wrapperFrame) {
                causeFrames.toMutableList().removeAt(causeFrameIndex)
            }
            causeFrameIndex--
            wrapperFrameIndex--
        }
    }
}