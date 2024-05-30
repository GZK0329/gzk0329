package com.gzk.gss.timewheel;

import com.gzk.gss.config.TaskTypeEnum;
import static com.gzk.gss.config.TaskTypeEnum.INTERVAL;

/**
 * @className: IntervalTaskContext
 * @description: TODO
 * @author: 70103
 * @date: 2024/5/29
 * @version: V8.2.300.0
 **/
public class IntervalTaskContext extends TimerTaskContext {

    /**
     * 任务类型
     */
    private final TaskTypeEnum type = INTERVAL;


    public IntervalTaskContext(Long delayMs, String desc) {
        super(delayMs, desc);
    }

    public IntervalTaskContext(Long delayMs) {
        super(delayMs);
    }

    @Override
    public TaskTypeEnum getType() {
        return type;
    }
}
