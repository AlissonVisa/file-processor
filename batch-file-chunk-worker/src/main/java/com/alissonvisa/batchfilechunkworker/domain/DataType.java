package com.alissonvisa.batchfilechunkworker.domain;

public class DataType {

    public static BusinessQueueType getBusinessQueueType(String line) {
        final String dataCode = line.substring(0,3);
        return BusinessQueueType.valueOfUsingCode(dataCode);
    }
}
