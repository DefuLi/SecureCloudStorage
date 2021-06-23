package cn.xjtu.iotlab.threads;

import cn.xjtu.iotlab.constant.MessageConstants;
import cn.xjtu.iotlab.service.MessageService;
import cn.xjtu.iotlab.service.impl.MessageServiceImpl;
import cn.xjtu.iotlab.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
@Component
public class MessageThreads {

    @Autowired
    MessageService messageService;

    private ExecutorService executorService;

    /**
     * 获取线程池
     *
     * @return ExecutorService对象
     */
    public ExecutorService getExecutorService() {
        executorService = new ThreadPoolExecutor(
                5, 10, 60L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        return executorService;
    }

    /**
     * 提交任务到线程池
     *
     * @param runnable   任务
     * @param authorUser 用户
     */
    public void executeTask(Runnable runnable, String authorUser) {
        ExecutorService executorService = getExecutorService();
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
                        log.info("unRead:{}", messageService.loopUnReadMessage(authorUser));
                        log.info("readed:{}", messageService.loopReadedMessage(authorUser));
                        log.info("trash:{}", messageService.loopTrashMessage(authorUser));
                        MessageConstants.unReadMap.put(authorUser, messageService.loopUnReadMessage(authorUser));
                        MessageConstants.readedMap.put(authorUser, messageService.loopReadedMessage(authorUser));
                        MessageConstants.trashMap.put(authorUser, messageService.loopTrashMessage(authorUser));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return runnable;
    }

}
