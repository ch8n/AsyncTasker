package dev.ch8n.handlerexample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        @SuppressLint("HandlerLeak")
        val task = object : AsyncTasker<String, Int, String>() {

            override fun doInBackground(vararg many: String): String {
                println("started doing work on ${Thread.currentThread().name}")
                (0..100).forEach {
                    onProgressUpdate(it)
                    Thread.sleep(100)
                }
                println("completed on ${Thread.currentThread().name}")

                return "done"
            }

            override fun onPostExecute(data: String) {

            }

            override fun onCompleted(isInterrupted: Boolean) {
                println("asyncTask onCompleted on ${Thread.currentThread().name} $isInterrupted")
            }

            override fun onPreExecute() {
                println("inside onPreExecute on ${Thread.currentThread().name} == UI ")
            }

            override fun onProgressUpdate(progress: Int) = runOnUiThread() {
                println("inside onProgressUpdate on ${Thread.currentThread().name} $progress")
            }

        }


        fab.setOnClickListener {
            task.execute()
        }

        fab.setOnLongClickListener {
            println("cancelled task.......")
            task.cancel()
            true
        }


    }


}
