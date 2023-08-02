package kt

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @DateTime 2023/5/16 14:24
 */
class MyCoroutine {
    @Test
    suspend fun d() {
        val launch = GlobalScope.launch {
            delay(1500)
            println("coroutine")

        }


        val coroutineScope1 = coroutineScope {//保证其作用域内的所 有代码和子协程在全部执行完之前，外部的协程会一直被挂起(不会继续运行,更不会结束
            val launch1 = launch {
                println(1)
                delay(1000)
                println("1 finish")
            }
            launch {
                println(2)
                delay(1000)
                println("2 finish")
            }
            launch.println()

        }
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }


        val job = Job()
        val coroutineScope = CoroutineScope(job)
        val job1 = coroutineScope.launch {
            println(1)
        }
        val result = coroutineScope.async {
            5 + 1
        }.await()
        println("async开启一个可以获取结果的协程,结果为$result")

        job.cancel()//顶层job取消,下面开始的协程也取消
        //region  async函数在声明之后立马await()会等待获取结果期间不允许其他协程启动,应该在其他协程之后await()获取结果  也就是串行改成并行
        runBlocking {
            val start = System.currentTimeMillis()
            val deferred1 = async {
                delay(1000)
                5 + 5
            }
            val deferred2 = async {
                delay(1000)
                4 + 6
            }
            println("result is ${deferred1.await() + deferred2.await()}.")
            val end = System.currentTimeMillis()
            println("cost ${end - start} milliseconds.")
        }

        //endregion

        val withContext = withContext(context = Dispatchers.Main) {
            //Dispatchers.Default //计算密集型
            Dispatchers.IO//IO密集型
            Dispatchers.Main//不会开启子线程,而是在主线程中执行代码
            5 + 8
        }
        println(withContext)

    }

    /**
    随着launch函数中的逻辑越来越复杂，可能你需要将部分代码提取到一个单独的函数
    delay()是个挂起函数用不了怎么办  suspend关键字将任意函数声明成挂起函数，而挂起函数之间都是可以互相调用的
    launch需要协程作用域怎么办? coroutineScope挂起函数,会继承外部的协程的作用域并创建一个子协程
     */
    private suspend fun printDot() = coroutineScope {
        println(".")
        delay(1000)
    }

    //region
    // suspendCoroutine函数必须在协程作用域或挂起函数中才能调用，它接收一个Lambda表达
    //式参数，主要作用是将当前协程立即挂起，然后在一个普通的线程中执行Lambda表达式中的
    //代码。Lambda表达式的参数列表上会传入一个Continuation参数，调用它的resume()方
//    //法或resumeWithException()可以让协程恢复执行。
//    suspend fun request(address: String): String {
//        return suspendCoroutine { continuation ->
//            HttpUtil.sendHttpRequest(address, object : HttpCallbackListener {
//                override fun onFinish(response: String) {
//                    continuation.resume(response)
//                }
//
//                override fun onError(e: Exception) {
//                    continuation.resumeWithException(e)
//                }
//            })
//        }
//    }
//    ，request()函数是一个挂起函数，并且接收一个address参数。在request()函
//    数的内部，我们调用了刚刚介绍的suspendCoroutine函数，这样当前协程就会被立刻挂起，
//    而Lambda表达式中的代码则会在普通线程中执行。接着我们在Lambda表达式中调用
//    HttpUtil.sendHttpRequest()方法发起网络请求，并通过传统回调的方式监听请求结果。
//    如果请求成功就调用Continuation的resume()方法恢复被挂起的协程，并传入服务器响应
//    的数据，该值会成为suspendCoroutine函数的返回值。如果请求失败，就调用
//    Continuation的resumeWithException()恢复被挂起的协程，并传入具体的异常原因。


//    suspend fun <T> Call<T>.await(): T {
//        return suspendCoroutine { continuation ->
//            enqueue(object : Callback<T> {
//                override fun onResponse(call: Call<T>, response: Response<T>) {
//                    val body = response.body()
//                    if (body != null) continuation.resume(body)
//                    else continuation.resumeWithException(
//                        RuntimeException("response body is null")
//                    )
//                }
//
//                override fun onFailure(call: Call<T>, t: Throwable) {
//                    continuation.resumeWithException(t)
//                }
//            })
//        }
//    }
//    val appList = ServiceCreator.create<AppService>().getAppData().await()


    //endregion
}