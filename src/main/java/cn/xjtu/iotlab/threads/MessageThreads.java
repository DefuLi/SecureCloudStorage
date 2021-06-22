package cn.xjtu.iotlab.threads;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

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
@Slf4j
public class MessageThreads {
    private static ExecutorService executorService;

    private static final MessageThreads instance = new MessageThreads();

    private MessageThreads() {
    }

    /**
     * 获取当前类的单例
     *
     * @return MessageThreads对象
     */
    public static final MessageThreads getInstance() {
        executorService = new ThreadPoolExecutor(
                5, 10, 60L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        return instance;
    }

    /**
     * 提交任务到线程池
     *
     * @param runnable   任务
     * @param authorUser 用户
     */
    public void executeTask(Runnable runnable, String authorUser) {
        executorService.execute(runnable);
        log.info("用户:{}的消息轮询任务已提交", authorUser);
    }

    /**
     * 创建任务
     *
     * @param authorUser 用户
     */
    public Runnable createTask(String authorUser) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        // TODO 查数据库

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        return runnable;
    }

}
