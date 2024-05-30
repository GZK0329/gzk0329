package com.gzk.gss;

import com.gzk.gss.timewheel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.util.TimeZone;
import java.util.concurrent.DelayQueue;

@SpringBootApplication()
@Slf4j
public class TimeWheelStarter {

    public static void main(String[] args) {

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(timeZone);
        TimerLauncher timerLauncher = TimerLauncher.newBuilder()
                .tickMs(1000L)
                .wheelSize(30)
                .currentTime(System.currentTimeMillis())
                .delayQueueThreadNum(5)
                .taskExecutorThreadNum(100)
                .defaultTimeout(200L)
                .build();

        IntervalTimerTask intervalTimerTask = (IntervalTimerTask) IntervalTimerTask.newBuilder().delayMs(10000L).runnable(() ->log.info("这是周期任务")).build();
        TimerTask timerTask = TimerTask.newBuilder().delayMs(1000L).runnable(() -> log.info("这是普通timerTask")).build();
        timerLauncher.add(intervalTimerTask);
        timerLauncher.add(timerTask);
        new SpringApplicationBuilder(TimeWheelStarter.class).run(args);
    }

}