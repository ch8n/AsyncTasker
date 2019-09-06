package dev.ch8n.handlerexample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var mainActHandler: Handler
    private lateinit var loaderManager: LoaderManager

    private lateinit var executor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainActHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when {
                    msg?.obj != null -> {
                        result.text = msg.obj.toString()
                    }
                    msg?.data != null -> {
                        result.text = msg.data.getString("test")
                    }
                }
            }
        }

        val downloadHandlerThread = DownloadHandlerThread()

        fab.setOnClickListener { view ->
            val (downloadHandler) = downloadHandlerThread

            downloadHandler.execute(Runnable {
                Log.d("DownloadHandler", "${Thread.currentThread().name} executed")
            }).execute(Runnable {
                mainActHandler.sendMessage(Message().also {
                    it.obj = "Pokemon downloaded"
                })
            })
        }


        val demoHandlerThread = DemoHandlerThread()

        //overrding for demoHandlerThread
        fab.setOnClickListener { view ->
            demoHandlerThread.execute(Runnable {
                Log.d("DownloadHandler", "${Thread.currentThread().name} executed")
            }).execute(Runnable {
                mainActHandler.sendMessage(Message.obtain().also {
                    it.data = Bundle().also {
                        it.putString("test", "pokemonResult")
                    }
                })
            })

        }


        executor = Executors.newFixedThreadPool(5)

        //overrding for executor example
        fab.setOnClickListener { view ->

            for (i in 0..10) {
                executor.execute(LongWork())
            }

        }




        loaderManager = LoaderManager.getInstance(this)
        val loader = loaderManager.initLoader(
            100,
            Bundle.EMPTY,
            object : LoaderManager.LoaderCallbacks<String> {
                override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
                    return AsyncLoader(this@MainActivity)
                }

                override fun onLoadFinished(loader: Loader<String>, data: String?) {

                }

                override fun onLoaderReset(loader: Loader<String>) {

                }

            })

        val loaderWithData = loaderManager.initLoader(
            101,
            Bundle().also {
                it.putString("test", "pokemon")
            },
            object : LoaderManager.LoaderCallbacks<String> {
                override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
                    return AsyncWithDataLoader(this@MainActivity, args)
                }

                override fun onLoadFinished(loader: Loader<String>, data: String?) {

                }

                override fun onLoaderReset(loader: Loader<String>) {

                }

            })

        //overrding for asyncLoading example
        fab.setOnClickListener { view ->
            //loader.forceLoad()
            loaderWithData.forceLoad()
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }


}
