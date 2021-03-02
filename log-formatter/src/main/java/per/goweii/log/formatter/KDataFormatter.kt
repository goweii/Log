package per.goweii.log.formatter

import per.goweii.log.LogFormatHandler
import per.goweii.log.LogFormatter
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class KDataFormatter : LogFormatter {
    @Suppress("UNCHECKED_CAST")
    override fun format(msg: Any, depth: Int, handler: LogFormatHandler): String? {
        if (!msg::class.isData) return null
        val itemDepth = depth + 1
        val kClass: KClass<Any> = msg::class as KClass<Any>
        val fields: Collection<KProperty1<Any, *>> = kClass.memberProperties
        val iMax = fields.size - 1
        val b = StringBuilder()
        b.append('{').append("\n")
        fields.forEachIndexed { i, field ->
            for (d in 0 until itemDepth) b.append(handler.depthPrefix)
            b.append(field.name)
            b.append("=")
            val value = field.get(msg)
            if (value === msg) {
                b.append("(this)")
            } else {
                b.append(handler.format(value, itemDepth))
            }
            if (i != iMax) {
                b.append(",")
                b.append("\n")
            } else {
                b.append("\n")
                for (d in 0 until depth) b.append(handler.depthPrefix)
                b.append('}')
            }
        }
        return b.toString()
    }
}