//package kt
//
//import java.awt.BorderLayout
//import java.awt.Graphics
//import java.net.URL
//import javax.imageio.ImageIO
//import javax.swing.JFrame
//import javax.swing.JPanel
//import javax.swing.WindowConstants
//
///**
// * @author ouyangyu369@gmail.com
// * @date 2022-06-26  16:36
// */
//
//fun main() {
////    var isOpen by remember { mutableStateOf(true) }
////
////    var isAskingToClose by remember { mutableStateOf(false) }
////
////    if (isOpen) {
////        Window(onCloseRequest = { isAskingToClose = true }) {
////            if (isAskingToClose) {
////                Dialog(onCloseRequest = { isAskingToClose = false }, title = "Close the document without saving?") {
////                    Button(onClick = { isOpen = false }) {
////                        Text("Yes")
////                    }
////                }
////            }
////        }
////    }
//    println(null + null)
//
//    JFrame("BoyNextDoor2").run {
//        layout = BorderLayout()
//        //run 内 精简的setA(BB) 赋值,有点像命名参数,不过本来这些属性就是JFrame的参数
//        //可能区别就在于{}里是赋值   ()里是命名参数
//        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
//        isVisible = true
//        val value = object : JPanel() {
//            override fun paintComponent(g: Graphics?) {
//                super.paintComponent(g)
//                g?.drawImage(
//                    ImageIO.read(URL("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF"))
//                        .apply { this@run.setSize(width, height) },//在这里回到JFrame的run设置image的属性
//                    0,
//                    0,
//                    this
//                )
//            }
//        }
//        add(value, BorderLayout.CENTER)
//    }
//
//}
