package com.alissonvisa.batchfilechunkworker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BusinessQueueType {

    SALESMAN("001"),
    SALES("003"),
    CUSTOMER("002");

    private String code;

    public static BusinessQueueType valueOfUsingCode(String code) {
        return Arrays.stream(BusinessQueueType.values())
                .filter(it->it.getCode().equals(code))
                .findAny()
                .orElse(null);
    }
}
