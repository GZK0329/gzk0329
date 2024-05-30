package com.gzk.gss.timewheel;

import lombok.extern.slf4j.Slf4j;

/**
 * @className: MyTimerTask
 * @description: TODO
 * @author: 70103
 * @date: 2024/5/29
 * @version: V8.2.300.0
 **/
@Slf4j
public class MyTimerTask extends TimerTask{

    public MyTimerTask(TimerTaskContext context) {
        super(context);
    }

    @Override
    public void run() {
        log.info("这是MyTimerTask执行");
    }

}
