package com.gzk.gss.timewheel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;

/**
 * @className: TimeWheel
 * @description:  时间轮
 * @author: 70103
 * @date: 2024/5/28
 * @version: V8.2.300.0
 **/
@Data
@Slf4j
public class TimeWheel {

    /**
     * 基本时间单位
     */
    private final long tickMs;

    /**
     * 时间单位个数
     */
    private final int wheelSize;

    /**
     * 总体时间跨度 interval = tickMs * wheelSize
     */
    private final long interval;

    /**
     * 当前所处时间
     */
    private long currentTime;

    /**
     * 任务列表数组
     */
    private TimerTaskList[] buckets;

    /**
     * 上一层时间轮
     */
    private volatile TimeWheel overflowWheel;

    /**
     * 阻塞队列，包含有bucket，给bucket按照其过期时间进行排序，减少空推进
     */
    private DelayQueue<TimerTaskList> delayQueue;

    public TimeWheel(long tickMs, int wheelSize, long currentTime, DelayQueue<TimerTaskList> delayQueue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.interval = tickMs * wheelSize;
        this.buckets = new TimerTaskList[wheelSize];
        this.currentTime = currentTime - (currentTime % tickMs);
        this.delayQueue = delayQueue;
        for (int i = 0; i < wheelSize; i++) {
            buckets[i] = new TimerTaskList();
        }
    }

    /**
     * 添加任务
     * @param entry
     * @return
     */
    public boolean add(TimerTaskEntry entry) {
        long expiration = entry.getExpireMs();
        if (expiration < currentTime + tickMs) {
            // 已经到期
            return false;
        } else if (expiration < currentTime + interval) {
            // 扔进当前时间轮的某个槽里
            long virtualId = (expiration / tickMs);
            int index = (int) (virtualId % wheelSize);
            TimerTaskList bucket = buckets[index];
            bucket.addTask(entry);
            // 设置bucket过期时间
            if (bucket.setExpiration(virtualId * tickMs)) {
                //重新入队
                delayQueue.offer(bucket);
                return true;
            }
        } else {
            // 扔到上一轮
            TimeWheel timeWheel = getOverflowWheel();
            return timeWheel.add(entry);
        }
        return false;
    }

    /**
     * 获取上层时间轮
     * @return
     */
    private TimeWheel getOverflowWheel() {
        if (overflowWheel == null) {
            synchronized (this) {
                if (overflowWheel == null) {
                    overflowWheel = new TimeWheel(interval, wheelSize, currentTime, delayQueue);
                }
            }
        }
        return overflowWheel;
    }

    /**
     * 推进指针
     *
     * @param timestamp 要推进的时间
     */
    public void advanceLock(long timestamp) {
        if (timestamp > currentTime + tickMs) {
            currentTime = timestamp - (timestamp % tickMs);
            if (overflowWheel != null) {
                // 找到最上层推进时间
                this.getOverflowWheel().advanceLock(timestamp);
            }
        }
    }

}

