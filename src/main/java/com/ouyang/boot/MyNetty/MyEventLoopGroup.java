package com.ouyang.boot.MyNetty;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-04 18:08
 */
@Slf4j
public class MyEventLoopGroup {
    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(2);//默认cpu核心数*2
        //io事件   普通任务   定时任务

        System.out.println(eventExecutors.next()); //下一个事件循环对象  走完会回到第一个

        eventExecutors.next().execute(() -> log.debug("ok"));//普通任务

        eventExecutors.next().scheduleAtFixedRate(
                () -> log.debug("11"), 0, 1, TimeUnit.SECONDS
        ); // 定时任务



    }

}
