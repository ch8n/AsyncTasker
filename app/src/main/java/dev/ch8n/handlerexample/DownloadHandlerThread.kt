package dev.ch8n.handlerexample

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log


interface IAsync<Params, Progress, Result> {
    fun doInBackground(vararg many: Params): Result
    fun onPostExecute(data: Result)
    fun onPreExecute()
    fun onProgressUpdate(progress: Progress)
    fun execute()
}


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


abstract class AsyncResolver<Params, Progress, Result> : IAsync<Params, Progress, Result>, Handler(Looper.getMainLooper()) {

    private val workerHandler = WorkerHandler()

    override fun execute() {
        val parentHandler = this
        println("execute on ${Thread.currentThread().name}")
        parentHandler.post { onPreExecute() }
        workerHandler.execute {
            println("inside worker handler start on ${Thread.currentThread().name}")
            val result: Result = doInBackground()
            println("inside worker handler end on ${Thread.currentThread().name}")
            parentHandler.post {
                onPostExecute(result)
            }
        }
    }
}






