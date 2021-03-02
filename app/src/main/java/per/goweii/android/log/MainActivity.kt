package per.goweii.android.log

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import per.goweii.log.Log
import per.goweii.log.Logger
import per.goweii.android.log.databinding.ActivityMainBinding
import per.goweii.log.formatter.*
import per.goweii.log.header.DefaultLogHeaderBuilder
import per.goweii.log.printer.android.AndroidLogPrinter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var logger: Logger
    private lateinit var logAdapter: LogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.setLevel(*Log.Level.VERBOSE_ABOVE)
        logger = Logger().apply {
            setLevel(*Log.Level.VERBOSE_ABOVE)
            setHeaderBuilder(DefaultLogHeaderBuilder())
            registerFormatter(ArrayFormatter())
            registerFormatter(CollectionFormatter())
            registerFormatter(BundleFormatter())
            registerFormatter(ClipDataFormatter())
            registerFormatter(IntentFormatter())
            registerFormatter(ThrowableFormatter())
            registerFormatter(KDataFormatter())
            addPrinter(AndroidLogPrinter)
        }

        logAdapter = LogAdapter()
        Log.addPrinter(logAdapter)
        binding.rvLogBoard.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = logAdapter
        }

        binding.btnLogBundle.setOnClickListener {
            logger.i {
                Bundle().apply {
                    putString("username", "goweii")
                    putBundle("bundle", this)
                }
            }
        }
        binding.btnLogClipdata.setOnClickListener {
            logger.w { ClipData.newIntent(null, intent) }
        }
        binding.btnLogList.setOnClickListener {
            logger.v {
                mutableListOf<UserBean>().apply {
                    for (i in 0 until Random.nextInt(5)) {
                        add(UserBean.random())
                    }
                }
            }
        }
        binding.btnLogIntent.setOnClickListener {
            logger.d { intent }
        }
        binding.btnLogThrowable.setOnClickListener {
            logger.e { NullPointerException("custom exception") }
        }
    }
}