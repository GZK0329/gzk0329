package com.gzk.gss.timewheel;

import com.gzk.gss.config.TaskTypeEnum;
import lombok.Data;

/**
 * @className: IntervalTimerTask
 * @description: 周期任务
 * @author: gzk0329
 * @date: 2024/5/30
 * @version: V1.0
 **/
@Data
public class IntervalTimerTask extends TimerTask {

    private final TaskTypeEnum type = TaskTypeEnum.INTERVAL;

    private long taskId;

    private long delayMs;

    private Runnable runnable;

    private String desc;

    private TimerTaskEntry timerTaskEntry;

    private long initDelayMs;

    public IntervalTimerTask() {
    }

    public TaskTypeEnum getType() {
        return type;
    }

    public static Builder newBuilder() {
        return new IntervalTimerTask.Builder();
    }

    public static class Builder {

        protected long taskId = -1L;

        protected long delayMs = 10000L;

        protected Runnable runnable = null;

        protected String desc = "默认说明";

        protected TimerTaskEntry timerTaskEntry = null;

        public Builder taskId(long taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder delayMs(long delayMs) {
            this.delayMs = delayMs;
            return this;
        }

        public Builder runnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public IntervalTimerTask build() {
            IntervalTimerTask intervalTimerTask = new IntervalTimerTask();
            intervalTimerTask.setTaskId(taskId);
            intervalTimerTask.setDelayMs(delayMs);
            intervalTimerTask.setRunnable(runnable);
            intervalTimerTask.setDesc(desc);
            intervalTimerTask.setTimerTaskEntry(null);
            return intervalTimerTask;
        }
    }
}
