package com.gzk.gss.timewheel;

/**
 * @className: TimerTask
 * @description: 定时任务
 * @author: gzk0329
 * @date: 2024/5/28
 * @version: V1.0
 **/

import com.gzk.gss.config.TaskTypeEnum;
import lombok.Data;

@Data
public abstract class TimerTask {

    protected long taskId;

    protected long delayMs;

    protected Runnable runnable;

    protected String desc;

    protected TimerTaskEntry timerTaskEntry;

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

    abstract TaskTypeEnum getType();
}
