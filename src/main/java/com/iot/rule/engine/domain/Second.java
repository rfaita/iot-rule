package com.iot.rule.engine.domain;

public class Second {

    private final Integer seconds;


    private Second(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getSeconds() {
        return this.seconds;
    }

    public Long getMilliseconds() {
        return getSeconds() * 1000L;
    }

    public static Second of(Integer seconds) {
        return new Second(seconds);
    }

}
