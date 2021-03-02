package per.goweii.log.formatter

import per.goweii.log.LogFormatHandler
import per.goweii.log.LogFormatter

class CollectionFormatter: LogFormatter {
    override fun format(msg: Any, depth: Int, handler: LogFormatHandler): String? {
        val array = msg as? Collection<*> ?: return null
        val iMax = array.size - 1
        if (iMax == -1) return "[]"
        val itemDepth = depth + 1
        val b = StringBuilder()
        b.append("[").append("\n")
        array.forEachIndexed { i, any ->
            for (d in 0 until itemDepth) b.append(handler.depthPrefix)
            if (any === msg) {
                b.append("(this)")
            } else {
                b.append(handler.format(any, itemDepth))
            }
            if (i == iMax) {
                b.append("\n")
                for (d in 0 until depth) b.append(handler.depthPrefix)
                b.append(']')
            } else {
                b.append(",")
                b.append("\n")
            }
        }
        return b.toString()
    }
}