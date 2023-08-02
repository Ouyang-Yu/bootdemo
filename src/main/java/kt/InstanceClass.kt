package kt


operator fun String.div(subString: String): Int {
    val windowed = this.windowed(size = subString.length, step = 1) {
        it == subString
    }
    return windowed.count {
        it //返回list中为ture的个数
    }
}


infix fun String.beginsWith(prefix: String) = startsWith(prefix)


fun Any.println() {
    println(this)
}

infix fun Int.add(any: Int) = this + any

fun main() {
    (1 add 2 add 3).println()
    ("aab" / "aa").println()
    if ("ab" beginsWith "a") {
        println(111)
    }

}


/**
 *  @time   2022-05-17 18:27
 *  @author ouyangyu369@gmail.com
 */
fun <T : Comparable<T>> myMax(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("Params can not be empty.")
    var maxNum = nums[0]
    for (num in nums) {
        if (num > maxNum) {
            maxNum = num
        }
    }
    return maxNum
}

inline fun <reified T> getGenericType() = T::class.java   //内联函数中的泛型进行实化


class MySet<T>(private val helperSet: HashSet<T>) : Set<T> by helperSet {
    //抽象方法具体实现 委托 helperSet这个参数 实现
    /**
     * ？既然都是调用辅助对象的方法实现，那还不如直接使用辅助
     * 对象得了。这么说确实没错，但如果我们只是让大部分的方法实现调用辅助对象中的方法，少
     * 部分的方法实现由自己来重写，甚至加入一些自己独有的方法，那么MySet就会成为一个全新
     * 的数据结构类，这就是委托模式的意义所在。
     */
}


