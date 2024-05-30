package com.gzk.gss;

import com.gzk.gss.timewheel.IntervalTaskContext;
import com.gzk.gss.timewheel.MyTimerTask;
import com.gzk.gss.timewheel.TimerLauncher;
import com.gzk.gss.timewheel.TimerTask;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.util.TimeZone;

@SpringBootApplication()
public class TimeWheelStarter {

    public static void main(String[] args) {

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(timeZone);
        TimerLauncher timerLauncher = new TimerLauncher();
        IntervalTaskContext intervalTaskContext = new IntervalTaskContext(10000L);
        TimerTask timerTask = new MyTimerTask(intervalTaskContext);
        timerLauncher.add(timerTask);
        new SpringApplicationBuilder(TimeWheelStarter.class).run(args);
    }

}