package com.gzk.gss;

import com.gzk.gss.timewheel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.util.TimeZone;

@SpringBootApplication( scanBasePackages = {
        "com.gzk.gss"
})
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

        IntervalTimerTask intervalTimerTask1 = (IntervalTimerTask) IntervalTimerTask.newBuilder().taskId(111L).delayMs(10000L).runnable(() ->log.info("这是周期任务11111")).build();
        timerLauncher.add(intervalTimerTask1);

        IntervalTimerTask intervalTimerTask2 = (IntervalTimerTask) IntervalTimerTask.newBuilder().taskId(222L).delayMs(10000L).runnable(() ->log.info("这是周期任务22222")).build();
        timerLauncher.add(intervalTimerTask2);

        IntervalTimerTask intervalTimerTask3 = (IntervalTimerTask) IntervalTimerTask.newBuilder().taskId(333L).delayMs(10000L).runnable(() ->log.info("这是周期任务33333")).build();
        timerLauncher.add(intervalTimerTask3);

        new SpringApplicationBuilder(TimeWheelStarter.class).run(args);
    }

}