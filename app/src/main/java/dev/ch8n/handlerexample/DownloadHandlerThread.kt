package dev.ch8n.handlerexample

import android.content.Context
import android.os.*
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import java.util.*

class DownloadHandlerThread : Thread() {

    lateinit var downloadHandler: DownloadHandler

    init {
        this.start()
    }

    override fun run() {
        super.run()
        Looper.prepare()
        downloadHandler = DownloadHandler()
        Looper.loop()
    }

    operator fun component1() = downloadHandler

}

class DownloadHandler : Handler() {

    fun execute(task: Runnable): DownloadHandler {
        this.post(task)
        return this
    }

    fun exit() {
        this.looper.quit()
    }

}

class DemoHandlerThread : HandlerThread("DemoThreadHandler") {

    private val handler: Handler

    init {
        this.start()
        // getLopper is blocking call
        handler = Handler(looper)
    }

    fun execute(task: Runnable): DemoHandlerThread {
        handler.post(task)
        return this
    }
}

class LongWork : Runnable {

    var workId: String = UUID.randomUUID().toString()

    override fun run() {
        Log.d("Executor", "started workID:$workId on ${Thread.currentThread().name}")
        Thread.sleep(4000)
        Log.d("Executor", "completed workID:$workId on ${Thread.currentThread().name}")
    }

}


//AsyncTaskLoader<[ReturnType]>
class AsyncLoader(context: Context) : AsyncTaskLoader<String>(context) {

    override fun loadInBackground(): String? {

        Log.d("AsyncLoader", "started on ${Thread.currentThread().name}")
        Thread.sleep(4000)
        Log.d("AsyncLoader", "completed on ${Thread.currentThread().name}")

        return null
    }

}


class AsyncWithDataLoader(context: Context, private val data: Bundle?) :
    AsyncTaskLoader<String>(context) {

    override fun loadInBackground(): String? {
        val work = data?.getString("test")
        Log.d("AsyncLoader", "started work:$work on ${Thread.currentThread().name}")
        Thread.sleep(4000)
        Log.d("AsyncLoader", "completed work:$work on ${Thread.currentThread().name}")
        return work
    }

    override fun deliverResult(data: String?) {
        val result = "$data also updated in delivery result";
        super.deliverResult(result)
    }
}