package com.gzk.gss.timewheel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @className: TimerTaskList
 * @description: 双向环形链表
 * @author: gzk0329
 * @date: 2024/5/28
 * @version: V1.0
 **/
@Data
@Slf4j
public class TimerTaskList implements Delayed {

    /**
     * 双向环形链表的虚拟头节点
     */
    private final TimerTaskEntry root;

    /**
     * bucket的过期时间
     */
    private final AtomicLong expiration;

    public TimerTaskList() {
        this.root = new TimerTaskEntry(null, -1);
        this.expiration = new AtomicLong(-1L);
        root.next = root;
        root.prev = root;
    }

    /**
     * 获取bucket过期时间
     *
     * @return
     */
    public long getExpiration() {
        return expiration.get();
    }

    /**
     * 设置bucket的过期时间
     *
     * @param expirationMs
     * @return
     */
    boolean setExpiration(long expirationMs) {
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    /**
     * 给链表添加节点
     *
     * @param entry 节点
     * @return
     */
    public boolean addTask(TimerTaskEntry entry) {
        boolean done = false;
        while (!done) {
            // 如果TimerTaskEntry已经在别的list中就先移除,同步代码块外面移除,避免死锁,一直到成功为止
            entry.remove();
            synchronized (this) {
                if (entry.timedTaskList == null) {
                    // 加到链表的末尾
                    entry.timedTaskList = this;
                    TimerTaskEntry tail = root.prev;
                    entry.prev = tail;
                    entry.next = root;
                    tail.next = entry;
                    root.prev = entry;
                    done = true;
                }
            }
        }
        return true;
    }

    /**
     * 从 TimedTaskList 移除 entry
     *
     * @param entry
     */
    public void remove(TimerTaskEntry entry) {
        synchronized (this) {
            if (entry.getTimedTaskList().equals(this)) {
                entry.next.prev = entry.prev;
                entry.prev.next = entry.next;
                entry.next = null;
                entry.prev = null;
                entry.timedTaskList = null;
            }
        }
    }

    /**
     * 移除List中所有的entry，过期时间设置为-1L
     */
    public synchronized void clear(Consumer<TimerTaskEntry> entry) {
        TimerTaskEntry head = root.next;
        while (!head.equals(root)) {
            remove(head);
            entry.accept(head);
            head = root.next;
        }
        expiration.set(-1L);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return Math.max(0, unit.convert(expiration.get() - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof TimerTaskList) {
            return Long.compare(expiration.get(), ((TimerTaskList) o).expiration.get());
        }
        return 0;
    }

}

