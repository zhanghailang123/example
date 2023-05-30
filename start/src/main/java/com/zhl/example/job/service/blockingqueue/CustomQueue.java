package com.zhl.example.job.service.blockingqueue;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hailang.zhang
 * @since 2023-05-26
 */
public class CustomQueue<T> {

    private int capacity;
    private LinkedList<T> queue = new LinkedList<T>();
    private Lock offerLock = new ReentrantLock();
    private Condition offerCondition = offerLock.newCondition();
    private Condition takeCondition = offerLock.newCondition();

    public CustomQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T t) {
        offerLock.lock();
        try {
            while (queue.size() == capacity) {
                System.out.println("队列满了，不再添加元素" + queue.size());
                takeCondition.signal();
                offerCondition.await();
            }
            queue.add(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            offerLock.unlock();
        }

    }

    public T take() {
        T t = null;
        offerLock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("队列空了，停止消费");
                offerCondition.signal();
                takeCondition.await();
            }
            t = queue.removeLast();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            offerLock.unlock();
        }

        return t;
    }
}