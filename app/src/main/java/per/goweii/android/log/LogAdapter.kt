package per.goweii.android.log

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import per.goweii.log.Log
import per.goweii.log.LogHeader
import per.goweii.log.LogPrinter
import per.goweii.android.log.databinding.RvItemLogItemBinding

class LogAdapter : RecyclerView.Adapter<LogAdapter.LogHolder>(), LogPrinter {
    private val list = mutableListOf<LogMsg>()
    private var recyclerView: RecyclerView? = null

    override fun print(level: Log.Level, tag: String?, header: LogHeader?, msg: String?) {
        add(LogMsg(level, tag, header, msg))
    }

    private fun add(data: LogMsg) {
        list.add(data)
        notifyItemInserted(list.lastIndex)
        recyclerView?.smoothScrollToPosition(list.lastIndex)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogHolder {
        return LogHolder(
            RvItemLogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LogHolder, position: Int) {
        holder.bindData(list[position])
    }

    class LogHolder(private val binding: RvItemLogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private fun View.visible(visible: Boolean) {
            visibility = if (visible) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        private fun TextView.setTextColorByLevel(level: Log.Level) {
            setTextColor(
                MaterialColors.getColor(
                    this, when (level) {
                        Log.Level.ASSERT -> R.attr.colorA
                        Log.Level.ERROR -> R.attr.colorE
                        Log.Level.WARN -> R.attr.colorW
                        Log.Level.INFO -> R.attr.colorI
                        Log.Level.DEBUG -> R.attr.colorD
                        Log.Level.VERBOSE -> R.attr.colorV
                    }
                )
            )
        }

        private fun View.setBackgroundResourceByLevel(level: Log.Level) {
            setBackgroundResource(
                when (level) {
                    Log.Level.ASSERT -> R.drawable.shape_log_a
                    Log.Level.ERROR -> R.drawable.shape_log_e
                    Log.Level.WARN -> R.drawable.shape_log_w
                    Log.Level.INFO -> R.drawable.shape_log_i
                    Log.Level.DEBUG -> R.drawable.shape_log_d
                    Log.Level.VERBOSE -> R.drawable.shape_log_v
                }
            )
        }

        @SuppressLint("SetTextI18n")
        fun bindData(data: LogMsg) {
            binding.tvTime.visible(!data.header?.timeFormat.isNullOrEmpty())
            binding.tvFile.visible(!data.header?.classInfo.isNullOrEmpty())
            binding.tvTag.visible(!data.tag.isNullOrEmpty())
            binding.tvMsg.visible(!data.msg.isNullOrEmpty())

            binding.root.setBackgroundResourceByLevel(data.level)
            binding.tvTime.setTextColorByLevel(data.level)
            binding.tvLevelAndThread.setTextColorByLevel(data.level)
            binding.tvFile.setTextColorByLevel(data.level)
            binding.tvTag.setTextColorByLevel(data.level)
            binding.tvMsg.setTextColorByLevel(data.level)

            binding.tvTime.text = data.header?.timeFormat
            binding.tvLevelAndThread.text = "${data.level.name}/${data.header?.threadName ?: ""}"
            binding.tvFile.text = data.header?.classInfo
            binding.tvTag.text = data.tag
            binding.tvMsg.text = data.msg
        }
    }
}