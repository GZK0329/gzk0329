package com.gzk.gss.timewheel;

/**
 * @className: TimerTask
 * @description: 定时任务
 * @author: 70103
 * @date: 2024/5/28
 * @version: V8.2.300.0
 **/
import com.gzk.gss.config.TaskTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class TimerTask<T extends TimerTaskContext, E extends TaskTypeEnum> implements Runnable {

    private String desc;

    private Long delayMs;

    private T context;

    private E taskType;

    public TimerTask(T context) {
        this.desc = context.getDesc();
        this.delayMs = context.getDelayMs();
        this.taskType = context.getType();
        this.context = context;
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return context.getTimerTaskEntry();
    }

    public synchronized void setTimerTaskEntry(TimerTaskEntry entry) {
        // 如果这个TimerTask已经被一个已存在的TimerTaskEntry持有,先移除一个
        if (context.getTimerTaskEntry() != null && context.getTimerTaskEntry() != entry) {
            context.getTimerTaskEntry().remove();
        }
        context.setTimerTaskEntry(entry);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public abstract void run();

}
