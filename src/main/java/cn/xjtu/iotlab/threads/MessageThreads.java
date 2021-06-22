package cn.xjtu.iotlab.threads;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 消息模块线程池
 *
 * @author Defu Li
 * @date 2021/6/22 8:43
 */

public class MessageThreads {
    public static final ExecutorService executorService = new ThreadPoolExecutor(
            5, 10, 60L,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));

    private static final MessageThreads instance = new MessageThreads();

    private MessageThreads() {
    }

    public static final MessageThreads getInstance() {
        return instance;
    }

}
