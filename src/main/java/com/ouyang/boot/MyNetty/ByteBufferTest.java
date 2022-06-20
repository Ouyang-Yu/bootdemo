package com.ouyang.boot.MyNetty;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;



/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-03 15:39
 */
public class ByteBufferTest {
    public static void main(String[] args) {
        //  position 当前指针    limit      capacity
        //写模式下 limit 在capacity位置
        //读模式下 limit到position    position归零


        ByteBuffer.allocate(10);//分配java堆内存,收GC影响
        ByteBuffer.allocateDirect(10);//直接分配系统内存



        /**
         * how to cast string to byteBuffer
         */
        ByteBuffer put = ByteBuffer.allocate(10).put("asd".getBytes()); //still write mode

        ByteBuffer wrap = ByteBuffer.wrap("asd".getBytes());

        ByteBuffer asd = StandardCharsets.UTF_8.encode("asd");
        String s = StandardCharsets.UTF_8.decode(asd).toString();

        /**
         * 分散读,集中写
         */

        /**
         * 粘包  半包
         */
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nni hao\nsh".getBytes());
        split(source);
        source.put("ijie/n".getBytes());
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i - source.position() + 1;
                ByteBuffer target = ByteBuffer.allocateDirect(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
            }
        }
        source.compact();
    }
}
