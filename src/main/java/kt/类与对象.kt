package kt

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @DateTime 2023/5/16 10:29
 */
fun main() {
    var a = object : Runnable { //匿名内部类
        override fun run() {
            println(1)
        }
    }
    var b: Runnable = Runnable { println(1) }
    var c = { println(1) }
    Thread(a).start()
    Thread(c).start()

}


open class Person(open val name: String, open val age: Int) {

}

class Student(
    override val name: String,
    override val age: Int,
    val grade: Int
) : Person(name, age) {
    //括号是因为子类的主构造函数必须调用父类的构造函数
    companion object {
        /**
         * companion object这个关键字实际上会
         * 在Util类的内部创建一个伴生类(单例的匿名内部类)，而doAction2()方法就是定义在这个伴生类里面的实例方
         * 法。只是Kotlin会保证Util类始终只会存在一个伴生类对象
         */
        @JvmStatic //能够在java中以静态方法形式调用
        fun before(): Unit {
            println("statis")
        }
    }

    init {
        println("created a student : $this") //主构造函数中的逻辑
    }

    constructor(name: String, grade: Int) : this(name, 18, grade)
    constructor() : this("name", 0) {//所有的次构造函数由constructor声明.都必须调用主构造函数（包括间接调用）
        println("no parameters constructor")//次构造函数中的逻辑
    }

    fun getType(): String {
        return "students"
    }

    fun study(): Unit {
        println("study")
    }

}
