package com.iot.rule.engine.domain;

public class Second {

    private final Integer seconds;


    public Second(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getSeconds() {
        return this.seconds;
    }

    public Long getMilliseconds() {
        return getSeconds() * 1000L;
    }

}
