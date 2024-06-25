package com.gzk.gss.timewheel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @className: TimerTaskHolder
 * @description: TODO
 * @author: 70103
 * @date: 2024/6/19
 * @version: V8.2.300.0
 **/
public class TimerTaskEntryHolder {
    public static ConcurrentMap<Long, TimerTaskEntry> TASK_MAP = new ConcurrentHashMap<>();
}
