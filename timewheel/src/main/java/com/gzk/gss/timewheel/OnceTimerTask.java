package com.gzk.gss.timewheel;

import com.gzk.gss.config.TaskTypeEnum;

/**
 * @className: OnceTimerTask
 * @description: TODO
 * @author: gzk0329
 * @date: 2024/6/19
 * @version: V1.0
 **/
public class OnceTimerTask extends TimerTask {
    private final TaskTypeEnum type = TaskTypeEnum.ONCE;

    public TaskTypeEnum getType() {
        return type;
    }
}
