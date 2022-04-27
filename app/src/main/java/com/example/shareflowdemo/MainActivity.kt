package com.example.shareflowdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val dataSource = PlayerItemDataSource()

    private lateinit var updateQueueBtn: Button
    private lateinit var updateIndexBtn: Button
    private lateinit var clearBtn: Button
    private lateinit var outputTv: TextView

    var outputStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateQueueBtn = findViewById(R.id.updateQueueBtn)
        updateIndexBtn = findViewById(R.id.updateIndexBtn)
        clearBtn = findViewById(R.id.clearBtn)
        outputTv = findViewById(R.id.outputTv)

        dataSource.sharedQueueFlow.onEach { list ->
            logMessage("update queue: $list")

            dataSource.sharedCurrentIndexFlow.first().let { index ->
                logMessage("update index: $index")
            }

        }.launchIn(lifecycleScope)


        updateQueueBtn.setOnClickListener {
            logMessage("---- update queue ----")
            val valueList = listOf("aaa", "bbb", "ccc", "AAA", "ABC", "DDD")
            val updateIndex = (0..3).random()
            val updateValue = valueList.random()
            dataSource.updateQueueFlow(updateIndex, updateValue)
        }

        updateIndexBtn.setOnClickListener {
            logMessage("---- update index ----")
            val updateIndex = (0..3).random()
            dataSource.updateCurrentIndex(updateIndex)
        }

        clearBtn.setOnClickListener {
            dataSource.resetItems()
            outputStr = ""
            outputTv.text = outputStr
        }

    }

    private fun logMessage(message: String) {
        outputStr += message + "\n"
        outputTv.text = outputStr

        Log.d("xxx", outputStr)
    }
}