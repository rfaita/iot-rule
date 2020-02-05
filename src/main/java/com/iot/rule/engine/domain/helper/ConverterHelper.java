package com.iot.rule.engine.domain.helper;

import java.math.BigDecimal;

public class ConverterHelper {

    private ConverterHelper() {
    }

    public static final BigDecimal convertToBigDecimal(Object data) {
        return new BigDecimal(data.toString());
    }
}
