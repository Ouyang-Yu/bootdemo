package kt


/**
 *  @time   2022-05-17 18:27
 *  @author ouyangyu369@gmail.com
 */



fun main() {
    val aaa = "aAcc"
    val message = aaa.replace('a', '1', ignoreCase = true)
    println(message)
    println(aaa)
    /**
     * 方法参数一定要有() 就像方法执行必须有()
     * lambda(即匿名函数) 返回值 别写return  最后一行代码自动返回
     */
    val aa = ::a

    val func: (Int) -> String =  { it->it.toString() } //声明一个函数的类型
    var func2 = fun(a: Int): String {
        return a.toString()   //这是定义函数体 ,所以要写return
    }
    //定义一个匿名函数赋值给

    println(aa(2, func))

}

fun a (s: Int, b: (Int) -> String): String {
    return b(s)
}



