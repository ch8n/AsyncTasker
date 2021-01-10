


# AsyncTasker
Self Implemented Async Task, cause Async Task is deprecated

# API design
This is a sample project api design is similar to that of asyncTask,

```Kotlin
abstract class AsyncTasker<Params, Progress, Result> {

    abstract fun doInBackground(vararg many: Params): Result
    abstract fun onPostExecute(data: Result)
    abstract fun onPreExecute()
    abstract fun onProgressUpdate(progress: Progress)
    
    /** Extra function that is called when every task complete **/
    abstract fun onCompleted(isInterrupted: Boolean)

    /** controller function **/
    fun execute(vararg many: Params) {
      ... doing stuff
    }

    /** function that interrupt underlying worker thread for immediate cancel of task **/
    fun cancel() {
       ... doing stuff
    }
}

```

Behind the Back uses `HandlerThread` as worker thread and handler to perform the task.

# Sample
1. click fab to perform async task
2. long click fab to perform cancel async task

# TODO
[] test cases
[] flag to check background task is active



