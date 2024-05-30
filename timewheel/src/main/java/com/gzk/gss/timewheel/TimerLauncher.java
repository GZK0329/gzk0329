package com.gzk.gss.timewheel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @className: TimerLauncher
 * @description: TODO
 * @author: gzk0329
 * @date: 2024/5/28
 * @version: V1.0
 **/
@Data
@Slf4j
public class TimerLauncher implements Timer {

    /**
     * 底层时间轮
     */
    private TimeWheel timeWheel;

    /**
     * 一个Timer只有一个延时队列
     */
    private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();

    /**
     * 任务执行线程
     */
    private ExecutorService workerThreadPool;

    /**
     * 轮询DelayQueue获取过期任务线程
     */
    private ExecutorService delayQueueThreadPool;

    public TimerLauncher() {
        //constructor
    }

    public void addTimerTaskEntry(TimerTaskEntry entry) {
        if (!timeWheel.add(entry)) {
            // 任务已到期
            TimerTask timerTask = entry.getTimerTask();
            log.info("=====任务:{} 已到期,准备执行============", timerTask.getDesc());
            workerThreadPool.submit(timerTask.getRunnable());
            add(timerTask);
        }
    }

    @Override
    public void add(TimerTask timerTask) {
        log.info("=======添加任务开始====task:{}", timerTask.getDesc());
        TimerTaskEntry entry = new TimerTaskEntry(timerTask, timerTask.getDelayMs() + System.currentTimeMillis());
        timerTask.setTimerTaskEntry(entry);
        addTimerTaskEntry(entry);
    }

    /**
     * 推动指针运转获取过期任务
     *
     * @param timeout 时间间隔
     * @return
     */
    @Override
    public synchronized void advanceClock(long timeout) {
        try {
            TimerTaskList bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                // 借助delayQueue 减少空推进，推进到期bucket的过期时间
                timeWheel.advanceLock(bucket.getExpiration());
                // 执行到期任务(包含降级)
                bucket.clear(this::addTimerTaskEntry);
            }
        } catch (Exception e) {
            log.error("推进时间轮异常");
        }
    }

    @Override
    public int size() {
        return 10;
    }

    @Override
    public void shutdown() {
        this.delayQueueThreadPool.shutdown();
        this.workerThreadPool.shutdown();
        this.timeWheel = null;
    }

    /**
     * 任务取消
     */
    @Override
    public void cancel() {

    }

    /**
     * 任务插队，将任务移动到另一个槽
     */
    @Override
    public void move() {

    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        long tickMs = 1000L;
        int wheelSize = 60;
        long currentTime = System.currentTimeMillis();
        int taskExecutorThreadNum = 100;
        int delayQueueThreadNum = 1;
        long defaultTimeout = 200L;

        public Builder tickMs(long tickMs){
            this.tickMs = tickMs;
            return this;
        }

        public Builder wheelSize(int wheelSize){
            this.wheelSize = wheelSize;
            return this;
        }

        public Builder currentTime(long currentTime){
            this.currentTime = currentTime;
            return this;
        }

        public Builder taskExecutorThreadNum(int taskExecutorThreadNum){
            this.taskExecutorThreadNum = taskExecutorThreadNum;
            return this;
        }

        public Builder delayQueueThreadNum(int delayQueueThreadNum){
            this.delayQueueThreadNum = delayQueueThreadNum;
            return this;
        }

        public Builder defaultTimeout(long defaultTimeout){
            this.defaultTimeout = defaultTimeout;
            return this;
        }


        public TimerLauncher build(){

            TimerLauncher timerLauncher = new TimerLauncher();

            timerLauncher.timeWheel = new TimeWheel(tickMs, wheelSize, currentTime, timerLauncher.getDelayQueue());
            timerLauncher.workerThreadPool = Executors.newFixedThreadPool(taskExecutorThreadNum);
            timerLauncher.delayQueueThreadPool = Executors.newFixedThreadPool(delayQueueThreadNum);
            // 20ms推动一次时间轮运转
            timerLauncher.delayQueueThreadPool.submit(() -> {
                while (true) {
                    timerLauncher.advanceClock(defaultTimeout);
                }
            });
            return timerLauncher;
        }
    }

}
