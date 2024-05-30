package com.gzk.gss.timewheel;

/**
 * @className: TimerTask
 * @description: 定时任务
 * @author: gzk0329
 * @date: 2024/5/28
 * @version: V1.0
 **/

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class TimerTask {

    protected long delayMs;

    protected Runnable runnable;

    protected String desc;

    protected TimerTaskEntry timerTaskEntry;

    public TimerTask() {
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }

    public synchronized void setTimerTaskEntry(TimerTaskEntry entry) {
        // 如果这个TimerTask已经被一个已存在的TimerTaskEntry持有,先移除一个
        if (timerTaskEntry != null && timerTaskEntry != entry) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = entry;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        protected long delayMs = 10000L;

        protected Runnable runnable = null;

        protected String desc = "默认说明";

        protected TimerTaskEntry timerTaskEntry = null;

        public Builder delayMs(long delayMs){
            this.delayMs = delayMs;
            return this;
        }

        public Builder runnable(Runnable runnable){
            this.runnable = runnable;
            return this;
        }

        public Builder desc(String desc){
            this.desc = desc;
            return this;
        }

        public TimerTask build(){
            TimerTask timerTask = new TimerTask();
            timerTask.setDelayMs(delayMs);
            timerTask.setRunnable(runnable);
            timerTask.setDesc(desc);
            timerTask.setTimerTaskEntry(null);
            return timerTask;
        }
    }
}
