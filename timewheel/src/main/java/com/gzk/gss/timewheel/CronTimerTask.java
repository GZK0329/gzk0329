package com.gzk.gss.timewheel;

import com.gzk.gss.config.TaskTypeEnum;

/**
 * @className: CronTimerTask
 * @description: TODO
 * @author: gzk0329
 * @date: 2024/6/20
 * @version: V1.0
 **/
public class CronTimerTask extends TimerTask {

    private final TaskTypeEnum type = TaskTypeEnum.CRON;

    public TaskTypeEnum getType() {
        return type;
    }
}
