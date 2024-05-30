package com.gzk.gss.timewheel;

import com.gzk.gss.config.TaskTypeEnum;
import lombok.Data;

/**
 * @className: TimerTaskContext
 * @description: 定时任务参数上下文
 * @author: 70103
 * @date: 2024/5/29
 * @version: V8.2.300.0
 **/

@Data
public class TimerTaskContext<T extends TaskTypeEnum> {

    /**
     * 延时时间
     */
    private long delayMs;

    /**
     * 定时任务类型 O
     */
    private T type;

    public TimerTaskContext(long delayMs, String desc) {
        this.delayMs = delayMs;
        this.timerTaskEntry = null;
        this.desc = desc;
    }

    public TimerTaskContext(long delayMs) {
        this.delayMs = delayMs;
        this.timerTaskEntry = null;
    }

    /**
     * 延时时间
     */
    private long delayMs;

    /**
     * 任务所在的entry
     */
    private TimerTaskEntry timerTaskEntry;

    private String desc;

}
