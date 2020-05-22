package com.github.niefy.modules.wx.service;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Fun Description //TODO
 * @Date 2020/5/22 17:06 22
 * @Author chenhj(brenda)
 * site: https://www.ant-loiter.com
 **/

public class ConcurrentLinkQueueTest {

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


    @Test
    void test(){
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    queue.offer("Index : " + index);
                }
            });
        }
        cachedThreadPool.shutdown();

        queue.offer("哈哈哈");
        System.out.println("offer后，队列是否空？" + queue.isEmpty());
        queue.stream().forEach(item -> {
            System.out.println(item);
            System.out.println("out items is : " + queue.poll());
        });
        System.out.println("从队列中poll：" + queue.poll());
        System.out.println("pool后，队列是否空？" + queue.isEmpty());
    }
}
