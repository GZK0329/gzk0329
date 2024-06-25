package com.gzk.gss.timewheel;

public interface Timer {

    /**
     * 添加一个新任务
     *
     * @param timerTask
     */
    void add(TimerTask timerTask);

    /**
     * 推动指针
     *
     * @param timeout
     */
    void advanceClock(long timeout);

    /**
     * 等待执行的任务
     *
     * @return
     */
    int size();

    /**
     * 关闭服务,剩下的无法被执行
     */
    void shutdown();

    /**
     * 任务取消
     */
    void cancel(long taskId);

    /**
     * 任务插队，将任务移动到另一个槽
     */
    void move();
}

