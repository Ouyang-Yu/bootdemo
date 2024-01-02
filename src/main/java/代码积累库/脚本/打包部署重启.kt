//package 代码积累库.脚本
//
//import com.jcraft.jsch.ChannelExec
//import com.jcraft.jsch.ChannelSftp
//import com.jcraft.jsch.JSch
//import java.io.FileInputStream
//
///**
// *  OuYang <a href="mailto:2455356027@qq.com">Mail</a>
// *  2023/8/2 9:25
// */
//class 打包部署重启 {
//
//    fun main() {
//        val localJarPath = "target/your-project.jar"
//        val remoteJarPath = "/home/app/wave-web-api/service/FbsAnalyze/your-project.jar"
//        val remoteCommand = "docker restart FbsAnalyze"
//
//        val sshHost = "your-ssh-host"
//        val sshUsername = "your-ssh-username"
//        val sshPassword = "your-ssh-password"
//
//        try {
//            val jsch = JSch()
//            val session: Session = jsch.getSession(sshUsername, sshHost, 22)
//            session.setPassword(sshPassword)
//            session.setConfig("StrictHostKeyChecking", "no")
//            session.connect()
//
//            val channelSftp: ChannelSftp = session.openChannel("sftp") as ChannelSftp
//            channelSftp.connect()
//
//            // Copy jar file to remote server
//            val localJarFile = File(localJarPath)
//            val inputStream = FileInputStream(localJarFile)
//            channelSftp.put(inputStream, remoteJarPath)
//            inputStream.close()
//
//            // Execute remote command
//            val channel = session.openChannel("exec")
//            (channel as ChannelExec).command = remoteCommand
//            channel.connect()
//            channel.disconnect()
//
//            channelSftp.disconnect()
//            session.disconnect()
//
//            println("Deployment completed successfully.")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//}