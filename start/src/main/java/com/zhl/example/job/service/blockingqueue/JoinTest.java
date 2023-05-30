package com.zhl.example.job.service.blockingqueue;

/**
 * @author hailang.zhang
 * @since 2023-05-30
 */
public class JoinTest {

    private static int count = 10;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            System.out.println(Thread.currentThread().getName() + "--开始执行 :" + count);
        });
        t1.start();
        t1.join();
        System.out.println(count);
    }
}