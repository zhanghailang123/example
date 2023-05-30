package com.zhl.example.job.service.blockingqueue;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author hailang.zhang
 * @since 2023-05-26
 */
public class ProducerTest{
    private volatile boolean flag = false;

    public static void main(String[] args) {
        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        CustomQueue<Integer> que = new CustomQueue<>(10);
        new Thread(() -> {
            Thread.currentThread().interrupt();
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = random.nextInt();
                que.offer(i);
                System.out.println("生产者生产了 ： " + i);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Integer take = que.take();
                System.out.println(Thread.currentThread().getName() + "消费者消费了：" + take);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Integer take = que.take();
                System.out.println(Thread.currentThread().getName() + "消费者消费了：" + take);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Integer take = que.take();
                System.out.println(Thread.currentThread().getName() + "消费者消费了：" + take);
            }
        }).start();
    }
}