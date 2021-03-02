package per.goweii.log.formatter

import android.content.ClipData
import per.goweii.log.LogFormatHandler
import per.goweii.log.LogFormatter

class ClipDataFormatter : LogFormatter {
    override fun format(msg: Any, depth: Int, handler: LogFormatHandler): String? {
        val clipData = msg as? ClipData ?: return null
        if (clipData.itemCount == 0) {
            return "{}"
        }
        val item = clipData.getItemAt(0)
        item ?: return "{}"
        val itemDepth = depth + 1
        val b = StringBuilder(128)
        b.append("{").append("\n")
        for (d in 0 until itemDepth) b.append(handler.depthPrefix)
        when {
            item.htmlText != null -> b.append("html=").append(item.htmlText)
            item.text != null -> b.append("text=").append(item.text)
            item.uri != null -> b.append("uri=").append(item.uri)
            item.intent != null -> b.append("intent=").append(handler.format(item.intent, itemDepth))
            else -> b.append("NULL")
        }
        b.append("\n")
        for (d in 0 until depth) b.append(handler.depthPrefix)
        b.append("}")
        return b.toString()
    }
}