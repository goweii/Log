package per.goweii.log.formatter

import android.os.Bundle
import per.goweii.log.LogFormatHandler
import per.goweii.log.LogFormatter

class BundleFormatter : LogFormatter {
    override fun format(msg: Any, depth: Int, handler: LogFormatHandler): String? {
        val bundle = msg as? Bundle ?: return null
        val keySet = bundle.keySet()
        if (keySet.isEmpty()) {
            return "{}"
        }
        val itemDepth = depth + 1
        val iterator = keySet.iterator()
        val b = StringBuilder(128)
        b.append("{").append("\n")
        while (true) {
            for (d in 0 until itemDepth) b.append(handler.depthPrefix)
            val key = iterator.next()
            val value = bundle.get(key)
            b.append(key).append('=')
            if (value === bundle) {
                b.append("(this)")
            } else {
                b.append(handler.format(value, itemDepth))
            }
            if (iterator.hasNext()) {
                b.append(",")
                b.append("\n")
            } else {
                b.append("\n")
                for (d in 0 until depth) b.append(handler.depthPrefix)
                b.append('}')
                break
            }
        }
        return b.toString()
    }
}