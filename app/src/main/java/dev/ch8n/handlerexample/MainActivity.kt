package dev.ch8n.handlerexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        @SuppressLint("HandlerLeak")
        val task = object : AsyncResolver<String, Int, String>() {

            override fun doInBackground(vararg many: String): String {
                Thread.sleep(2000)
                println("completed on ${Thread.currentThread().name}")

                return "done"
            }

            override fun onPostExecute(data: String) {
                println("inside onPostExecute on ${Thread.currentThread().name}  == UI")
            }

            override fun onPreExecute() {
                println("inside onPreExecute on ${Thread.currentThread().name} == UI ")
            }

            override fun onProgressUpdate(progress: Int)  = runOnUiThread(){
                println("inside onProgressUpdate on ${Thread.currentThread().name} == Worker")
            }

        }

        fab.setOnClickListener {
            task.execute()
        }
    }


}
