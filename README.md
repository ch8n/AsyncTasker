<h1 align="center"><a href="https://chetangupta.net/" target="_blank">AndroidBites</a>
</h1>

![AndroidBites](./androidbites_space.jpg)

## :hand: About
Hola Amigos! 🙌, welcome to my coding playground! go on explore around 👩‍💻

Do let me know if you find something useful or want to suggest some improvement
also don't forget to checkout of blogs at [`chetangupta.net`](https://chetangupta.net/)

## :eyes: Social
[LinkedIn](https://bit.ly/ch8n-linkdIn) |
[Medium](https://bit.ly/ch8n-medium-blog) |
[Twitter](https://bit.ly/ch8n-twitter) |
[StackOverflow](https://bit.ly/ch8n-stackOflow) |
[CodeWars](https://bit.ly/ch8n-codewar) |
[Portfolio](https://bit.ly/ch8n-home) |
[Github](https://bit.ly/ch8n-git) |
[Instagram](https://bit.ly/ch8n-insta) |
[Youtube](https://bit.ly/ch8n-youtube)


# AsyncTasker
[Pooja's AsyncTask Challenge](https://pooja-srivs.medium.com/build-your-own-asynctask-fcfc4aaf0225) 
Self Implemented Async Task for Pooja Srivs Challenge

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
* test cases
* flag to check background task is active


## :cop: License
Shield: [![CC BY-SA 4.0][cc-by-sa-shield]][cc-by-sa]

This work is licensed under a
[Creative Commons Attribution-ShareAlike 4.0 International License][cc-by-sa].

[![CC BY-SA 4.0][cc-by-sa-image]][cc-by-sa]

[cc-by-sa]: http://creativecommons.org/licenses/by-sa/4.0/
[cc-by-sa-image]: https://licensebuttons.net/l/by-sa/4.0/88x31.png
[cc-by-sa-shield]: https://img.shields.io/badge/License-CC%20BY--SA%204.0-lightgrey.svg
