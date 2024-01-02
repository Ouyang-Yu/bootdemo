package kt

import org.junit.jupiter.api.Test

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @DateTime 2023/5/15 16:49
 */


class KTFun {
    @Test
    fun d() {
        fun a(s: Int, b: (Int) -> String): String {
            return b(s)
        }


        val aa = ::a

        val function: (Int) -> String = { it.toString() } //将函数类型的变量定义为lambda,最后一行自动返回
        println(aa(2, function))
        fun aaa(a: Int): String {
            return a.toString()   //这是定义函数体 ,所以要写return
        }

        var fun3 = ::aaa

        //定义一个匿名函数赋值给
    }
}