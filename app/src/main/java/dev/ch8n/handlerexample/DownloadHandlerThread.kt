package dev.ch8n.handlerexample

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper

class WorkerHandler : HandlerThread("worker thread!") {

    private val handler: Handler

    init {
        this.start()
        handler = Handler(looper)
    }


    fun execute(task: () -> Unit): WorkerHandler {
        handler.post {
            task.invoke()
        }
        return this
    }

}


abstract class AsyncTasker<Params, Progress, Result> {

    abstract fun doInBackground(vararg many: Params): Result
    abstract fun onPostExecute(data: Result)
    abstract fun onPreExecute()
    abstract fun onCompleted(isInterrupted: Boolean)
    abstract fun onProgressUpdate(progress: Progress)


    private val _workerHandler = WorkerHandler()
    private var _isInterrupted: Boolean = false

    fun execute(vararg many: Params) {
        val parentHandler = Handler(Looper.getMainLooper())
        println("execute on ${Thread.currentThread().name}")
        parentHandler.post { onPreExecute() }
        _workerHandler.execute {
            println("inside worker handler start on ${Thread.currentThread().name}")
            val result = runCatching { doInBackground(*many) }.getOrNull()
            _isInterrupted = result == null
            if (result != null) {
                parentHandler.post { onPostExecute(result) }
            }
            parentHandler.post { onCompleted(_isInterrupted) }
            println("end of worker on ${Thread.currentThread().name}")
        }
    }


    fun cancel() {
        _workerHandler.interrupt()
        _isInterrupted = true
    }
}






