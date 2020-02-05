package com.iot.rule.engine.helper;

import com.iot.rule.engine.domain.IngestionData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class IngestionDataHelper {


    public static IngestionData createNumericIngestionData(Integer numericValue) {
        return createDefaultIngestionData(numericValue, null);
    }

    public static IngestionData createStringIngestionData(String stringValue) {

        return createDefaultIngestionData(null, stringValue);
    }

    public static IngestionData createDefaultIngestionData(
            Integer numericValue,
            String stringValue) {

        Map<String, Object> data = new HashMap<>();

        data.put("numericTest", numericValue);
        data.put("stringTest", stringValue);

        return new IngestionData("deviceId", 0l, "customerId", data);
    }

}
