package dev.ch8n.handlerexample

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log

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


abstract class AsyncResolver<Params, Progress, Result> {

    abstract fun doInBackground(vararg many: Params): Result
    abstract fun onPostExecute(data: Result)
    abstract fun onPreExecute()
    abstract fun onProgressUpdate(progress: Progress)

    private val workerHandler = WorkerHandler()

    fun execute(vararg many: Params) {
        val parentHandler = Handler(Looper.getMainLooper())
        println("execute on ${Thread.currentThread().name}")
        parentHandler.post { onPreExecute() }
        workerHandler.execute {
            println("inside worker handler start on ${Thread.currentThread().name}")
            val result: Result = doInBackground(*many)
            println("inside worker handler end on ${Thread.currentThread().name}")
            parentHandler.post {
                onPostExecute(result)
            }
        }
    }
}






