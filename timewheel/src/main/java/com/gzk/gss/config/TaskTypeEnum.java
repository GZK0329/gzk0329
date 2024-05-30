package com.gzk.gss.config;

public enum TaskTypeEnum implements IBaseEnum<String> {

    CRON("cron型周期任务"),

    INTERVAL("周期型任务"),

    ONCE("单次执行任务");

    private String value;

    TaskTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
