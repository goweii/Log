package per.goweii.log.formatter

import android.content.Intent
import per.goweii.log.LogFormatHandler
import per.goweii.log.LogFormatter

class IntentFormatter : LogFormatter {
    override fun format(msg: Any, depth: Int, handler: LogFormatHandler): String? {
        val intent = msg as? Intent ?: return null
        val b = StringBuilder(128)
        b.append("{")
        var first = true
        val itemDepth = depth + 1
        fun appendDepthPrefix() {
            if (!first) b.append(",")
            first = false
            b.append("\n")
            for (d in 0 until itemDepth) b.append(handler.depthPrefix)
        }
        intent.action?.let {
            appendDepthPrefix()
            b.append("action=").append(it)
        }
        intent.categories?.let {
            appendDepthPrefix()
            b.append("categories=")
            b.append(handler.format(it, itemDepth))
        }
        intent.data?.let {
            appendDepthPrefix()
            b.append("data=").append(it)
        }
        intent.type?.let {
            appendDepthPrefix()
            b.append("type=").append(it)
        }
        if (intent.flags != 0) {
            appendDepthPrefix()
            b.append("flags=")
            b.append("(")
            b.append("0x").append(Integer.toHexString(intent.flags))
            b.append(")")
            val flagList = parseFlags(intent.flags)
            b.append(handler.format(flagList, itemDepth))
        }
        intent.`package`?.let {
            appendDepthPrefix()
            b.append("package=").append(it)
        }
        intent.component?.let {
            appendDepthPrefix()
            b.append("component=").append(it.flattenToShortString())
        }
        intent.sourceBounds?.let {
            appendDepthPrefix()
            b.append("sourceBounds=").append(it.toShortString())
        }
        if (intent.clipData != null) {
            appendDepthPrefix()
            b.append("clipData=").append(handler.format(intent.clipData, itemDepth))

        }
        intent.extras?.let {
            appendDepthPrefix()
            b.append("extras=")
            b.append(handler.format(it, itemDepth))
        }
        intent.selector?.let {
            appendDepthPrefix()
            b.append("selector=")
            if (it === intent) {
                b.append("(this)")
            } else {
                b.append(handler.format(it, itemDepth))
            }
        }
        if (!first) {
            b.append("\n")
            for (d in 0 until depth) b.append(handler.depthPrefix)
        }
        b.append("}")
        return b.toString()
    }

    private fun parseFlags(flags: Int): List<String> {
        val list = mutableListOf<String>()
        if (flags and Intent.FLAG_GRANT_READ_URI_PERMISSION != 0) list.add("FLAG_GRANT_READ_URI_PERMISSION")
        if (flags and Intent.FLAG_GRANT_WRITE_URI_PERMISSION != 0) list.add("FLAG_GRANT_WRITE_URI_PERMISSION")
        if (flags and Intent.FLAG_FROM_BACKGROUND != 0) list.add("FLAG_FROM_BACKGROUND")
        if (flags and Intent.FLAG_DEBUG_LOG_RESOLUTION != 0) list.add("FLAG_DEBUG_LOG_RESOLUTION")
        if (flags and Intent.FLAG_EXCLUDE_STOPPED_PACKAGES != 0) list.add("FLAG_EXCLUDE_STOPPED_PACKAGES")
        if (flags and Intent.FLAG_INCLUDE_STOPPED_PACKAGES != 0) list.add("FLAG_INCLUDE_STOPPED_PACKAGES")
        if (flags and Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION != 0) list.add("FLAG_GRANT_PERSISTABLE_URI_PERMISSION")
        if (flags and Intent.FLAG_GRANT_PREFIX_URI_PERMISSION != 0) list.add("FLAG_GRANT_PREFIX_URI_PERMISSION")
        if (flags and Intent.FLAG_ACTIVITY_MATCH_EXTERNAL != 0) list.add("FLAG_ACTIVITY_MATCH_EXTERNAL")
        if (flags and Intent.FLAG_ACTIVITY_NO_HISTORY != 0) list.add("FLAG_ACTIVITY_NO_HISTORY")
        if (flags and Intent.FLAG_ACTIVITY_SINGLE_TOP != 0) list.add("FLAG_ACTIVITY_SINGLE_TOP")
        if (flags and Intent.FLAG_ACTIVITY_NEW_TASK != 0) list.add("FLAG_ACTIVITY_NEW_TASK")
        if (flags and Intent.FLAG_ACTIVITY_MULTIPLE_TASK != 0) list.add("FLAG_ACTIVITY_MULTIPLE_TASK")
        if (flags and Intent.FLAG_ACTIVITY_CLEAR_TOP != 0) list.add("FLAG_ACTIVITY_CLEAR_TOP")
        if (flags and Intent.FLAG_ACTIVITY_FORWARD_RESULT != 0) list.add("FLAG_ACTIVITY_FORWARD_RESULT")
        if (flags and Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP != 0) list.add("FLAG_ACTIVITY_PREVIOUS_IS_TOP")
        if (flags and Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS != 0) list.add("FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS")
        if (flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) list.add("FLAG_ACTIVITY_BROUGHT_TO_FRONT")
        if (flags and Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED != 0) list.add("FLAG_ACTIVITY_RESET_TASK_IF_NEEDED")
        if (flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY != 0) list.add("FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY")
        if (flags and Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET != 0) list.add("FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET")
        if (flags and Intent.FLAG_ACTIVITY_NEW_DOCUMENT != 0) list.add("FLAG_ACTIVITY_NEW_DOCUMENT")
        if (flags and Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET != 0) list.add("FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET")
        if (flags and Intent.FLAG_ACTIVITY_NO_USER_ACTION != 0) list.add("FLAG_ACTIVITY_NO_USER_ACTION")
        if (flags and Intent.FLAG_ACTIVITY_REORDER_TO_FRONT != 0) list.add("FLAG_ACTIVITY_REORDER_TO_FRONT")
        if (flags and Intent.FLAG_ACTIVITY_NO_ANIMATION != 0) list.add("FLAG_ACTIVITY_NO_ANIMATION")
        if (flags and Intent.FLAG_ACTIVITY_CLEAR_TASK != 0) list.add("FLAG_ACTIVITY_CLEAR_TASK")
        if (flags and Intent.FLAG_ACTIVITY_TASK_ON_HOME != 0) list.add("FLAG_ACTIVITY_TASK_ON_HOME")
        if (flags and Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS != 0) list.add("FLAG_ACTIVITY_RETAIN_IN_RECENTS")
        if (flags and Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT != 0) list.add("FLAG_ACTIVITY_LAUNCH_ADJACENT")
        if (flags and Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER != 0) list.add("FLAG_ACTIVITY_REQUIRE_NON_BROWSER")
        if (flags and Intent.FLAG_ACTIVITY_REQUIRE_DEFAULT != 0) list.add("FLAG_ACTIVITY_REQUIRE_DEFAULT")
        if (flags and Intent.FLAG_RECEIVER_REGISTERED_ONLY != 0) list.add("FLAG_RECEIVER_REGISTERED_ONLY")
        if (flags and Intent.FLAG_RECEIVER_REPLACE_PENDING != 0) list.add("FLAG_RECEIVER_REPLACE_PENDING")
        if (flags and Intent.FLAG_RECEIVER_FOREGROUND != 0) list.add("FLAG_RECEIVER_FOREGROUND")
        if (flags and Intent.FLAG_RECEIVER_NO_ABORT != 0) list.add("FLAG_RECEIVER_NO_ABORT")
        if (flags and Intent.FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS != 0) list.add("FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS")
        return list
    }
}