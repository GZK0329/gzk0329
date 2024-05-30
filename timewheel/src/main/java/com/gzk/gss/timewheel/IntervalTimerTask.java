package com.gzk.gss.timewheel;

import com.gzk.gss.config.TaskTypeEnum;

/**
 * @className: IntervalTimerTask
 * @description: TODO
 * @author: 70103
 * @date: 2024/5/30
 * @version: V8.2.300.0
 **/
public class IntervalTimerTask extends TimerTask{

    private final TaskTypeEnum type = TaskTypeEnum.INTERVAL;

    public IntervalTimerTask() {
    }

    public static IntervalTimerTask.Builder newBuilder(){
        return new IntervalTimerTask.Builder();
    }

    public static class Builder extends TimerTask.Builder{

        public IntervalTimerTask build(){
            IntervalTimerTask intervalTimerTask = new IntervalTimerTask();
            intervalTimerTask.setDelayMs(delayMs);
            intervalTimerTask.setRunnable(runnable);
            intervalTimerTask.setDesc(desc);
            intervalTimerTask.setTimerTaskEntry(null);
            return intervalTimerTask;
        }
    }
}
