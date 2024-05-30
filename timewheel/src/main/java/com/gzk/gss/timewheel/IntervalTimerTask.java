package com.gzk.gss.timewheel;

import com.gzk.gss.config.TaskTypeEnum;

/**
 * @className: IntervalTimerTask
 * @description: 周期任务
 * @author: gzk0329
 * @date: 2024/5/30
 * @version: V1.0
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
