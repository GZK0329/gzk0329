package com.gzk.gss.timewheel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @className: TimerTaskEntry
 * @description: 双向环形链表节点
 * @author: 70103
 * @date: 2024/5/28
 * @version: V8.2.300.0
 **/
@Data
@Slf4j
public class TimerTaskEntry implements Comparable<TimerTaskEntry> {

    volatile TimerTaskList timedTaskList;

    TimerTaskEntry next;

    TimerTaskEntry prev;

    private TimerTask timerTask;

    private long expireMs;

    public TimerTaskEntry(TimerTask timedTask, long expireMs) {
        this.timerTask = timedTask;
        this.expireMs = expireMs;
        this.next = null;
        this.prev = null;
    }

    /**
     * 从TimerTaskList移除Entry
     */
    void remove() {
        TimerTaskList currentList = timedTaskList;
        //移除成功，跳出循环
        while (currentList != null) {
            //从list中移除entry，把entry的timedTaskList设置为null
            currentList.remove(this);
            currentList = timedTaskList;
        }
    }

    /**
     * 按照过期时间，升序排序
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(TimerTaskEntry o) {
        return ((int) (this.expireMs - o.expireMs));
    }

}
