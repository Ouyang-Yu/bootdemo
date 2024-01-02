package kt

import org.junit.jupiter.api.Test
import java.awt.BorderLayout
import java.awt.Graphics
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

class DSLTest {


    class Dependency {
        val libraries = ArrayList<String>()
        fun implementation(lib: String) {
            libraries.add(lib)
        }
    }


    private fun dependencies(block: Dependency.() -> Unit): ArrayList<String> {


        return Dependency().apply(block).libraries  //Graceful

//        return Dependency().apply {
//            this.block()
//        }.libraries

//        val dependency = Dependency()
//        dependency.block()
//        return dependency.libraries
    }

    @Test
    fun myDSL() {
        dependencies {
            implementation("com.squareup.retrofit2:retrofit:2.6.1")
            implementation("com.squareup.retrofit2:converter-gson:2.6.1")
        }
            .forEach(::println)

        Dependency().apply {
            implementation("lib1")
            implementation("lib3")
        }.libraries.forEach(::println)
    }
}

val student: Student = Student()

fun main() {

    "".split(",")


//region 这是可以给ide识别为可以折叠的区域注释
    println(1)
//endregion


    fun large(a: Int, b: Int) = if (a >= b) a else b //kotlin中的三目运算


    for (index in 0..9) {
        println(index)
    }
    var range = 0 until 10 step 1
    var down = 10 downTo 1

    val listOf = listOf("a", "b")
    val mapOf = mapOf(
        1 to "a",
        2 to "b"
    )
    for ((index, str) in mapOf) { //解构
        println(" ${index.dec()} -> $str")
    }

    //空指针
    //1.可空对象的方法安全调用
    //2.可空对象给个默认值,可以转为非空对象
    //3.非空断言
    fun getTextLength(text: String?) = text?.length ?: 0


    val let = student?.let {
        //let这个标准函数，它的主要作用就是配合?.操作符来进行 辅助判空处理
        it.getType().println()
        it.study()
    }
    val also = student?.also {
        it.getType().println()
        it.study()
    }

    val apply = student?.apply {//返回调用对象本身
        getType().println()
        study()
        this.getType()
    }
    val run = student?.run {//把最后一行当返回值,如果最后一行为this,返回调用对象本身
        this.getType().println()
        this.study()

        this

    }
    val str = student?.run {
        getType().println()
        study()
    }
    "sd".countLetters().println()


    println(Student())


}

fun String.countLetters(): Number {
//        val pred = { it: String -> it[0].isLetter() }
//        return this.windowed(1, 1){
//            it[0].isLetter()
//        }.count {
//            it
//        }
    val lambda = { it: String ->
        it.countLetters().println()
        it.length
    }//kotlin中的lambda  1.外面大括号 2.参数和类型 3.函数体,最后一行作为返回值
    //如果声明了函数类型,可以只写函数体,省略参数类型和箭头,如果只有一个参数,可以省略默认it
    val lambda1: (String) -> Int = {
        it.countLetters().println()
        it.length
    }

    /**
     * Kotlin高阶函数背后的实现原理
     * Lambda表达式在底层
     * 被转换成了匿名类的实现方式。这就表明，我们每调用一次Lambda表达式，都会创建一个新
     * 的匿名类实例，当然也会造成额外的内存和性能开销
     * 为了解决这个问题，Kotlin提供了内联函数的功能，它可以将使用Lambda表达式带来的运行时
     * 开销完全消除。
     *
     *
     * Lambda表达式中是不允许直接
     * 使用return关键字的，这里使用了return@printString的写法，表示进行局部返回，并且
     * 不再执行Lambda表达式的剩余部分代码。
     *
     *
     * 前面我们已经解释了内联函数的好处，那么为什么Kotlin还要提供一个noinline关键字来排除
     * 内联功能呢？这是因为内联的函数类型参数在编译的时候会被进行代码替换，因此它没有真正
     * 的参数属性。非内联的函数类型参数可以自由地传递给其他任何函数，因为它就是一个真实的
     * 参数，而内联的函数类型参数只允许传递给另外一个内联函数，这也是它最大的局限性。
     *
     *将高阶函数声明成内联函数是一种良好的编程习惯，事实上，绝大多数高阶函数是可以直接声
     * 明成内联函数的，
     *内联函数的Lambda表达式中允许使用return关键字，和高阶函数的匿名类实
     * 现中不允许使用return关键字之间造成了冲突。而crossinline关键字就像一个契约，它用
     * 于保证在内联函数的Lambda表达式中一定不会使用return关键字
     */
    val predicate = { fruit: Boolean ->
        println(fruit)
        fruit
    }
    return this.chars()
        .mapToObj {
            it.toChar().isLetter()
        }.filter(predicate).count()
}

inline fun runRunnable(crossinline block: () -> Unit) {
    Runnable {
        block()
    }.apply {
        run()
    }
}


class MyTest {


    @Test
    fun jFrame() {
         JFrame("BoyNextDoor2").run {
            layout = BorderLayout()
            //run 内 精简的setA(BB) 赋值,有点像命名参数,不过本来这些属性就是JFrame的参数
            //可能区别就在于{}里是赋值   ()里是命名参数
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE;
            isVisible = true

            add(
                object : JPanel() {
                    override fun paintComponent(graphics: Graphics?) {
                        super.paintComponent(graphics)
                        graphics?.drawImage(
                            ImageIO.read(URL("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF"))
                                .apply {
                                    this@run.setSize(this.width, height) //这里的this默认是小范围的image对象,@run函数指明是run里面的this
                                },
                            //在这里只要一个image对象就可以
                            // 但是可以通过apply使用image对象的属性
                            // 回到JFrame的run函数体里面setSize,外面也可以用内部的属性
                            //好处是不用在外面声明image对象
                            0,
                            0,
                            this
                        )
                    }
                },
                BorderLayout.CENTER
            )
        }
    }
}
