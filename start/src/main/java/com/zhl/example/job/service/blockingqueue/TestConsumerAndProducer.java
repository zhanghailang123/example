package com.zhl.example.job.service.blockingqueue;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hailang.zhang
 * @since 2023-05-30
 */
public class TestConsumerAndProducer {

    private static Object lock = new Object();

    private static AtomicInteger size = new AtomicInteger(20);

    private static LinkedList<Integer> data = new LinkedList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread((() -> {
                produce();
            })).start();
        }
        //是否具备写长代码 设计和编程的能力呢 came back 你所作的事 和你追求的事有没有必然联系 如果没有的话希望你好好想以下
        //并且
        //get restart 假如今天的你被裁了 那么你有没有准备好 或者说有没有能力
        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    System.out.println("消费者开始消费了");
                    while (data.isEmpty()) {
                        System.out.println("缓冲区为空，停止消费");
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Integer i = data.removeLast();
                    System.out.println("消费者消费了：" + i);
                    lock.notify();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private static void produce() {
        while (true) {
            System.out.println("生产者开始生产");
            synchronized (lock) {
                while (data.size() == 10) {
                    System.out.println("缓冲区已满，停止生产");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Random random = new Random();
                int i = random.nextInt(1000000);
                System.out.println("生产者生产了：" + i);
                data.add(i);
                lock.notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}