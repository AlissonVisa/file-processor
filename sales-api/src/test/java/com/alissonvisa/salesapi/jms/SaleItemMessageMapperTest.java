package com.alissonvisa.salesapi.jms;

import com.alissonvisa.salesapi.domain.SaleItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleItemMessageMapperTest {

    SaleItemMessageMapper saleItemMessageMapper;

    @Test
    void map() {

        saleItemMessageMapper = new SaleItemMessageMapper();

        String[] messages = new String[] {
                "1-10-100",
                "2-30-2.50",
                "3-40-3.10"
        };

        assertEquals(saleItemMessageMapper.map(messages), List.of(
                new SaleItem(1l,10l, BigDecimal.valueOf(100l)),
                new SaleItem(2l,30l, new BigDecimal("2.50")),
                new SaleItem(3l,40l, new BigDecimal("3.10"))
        ));

    }

    @Test
    void map_InvalidLine_ThrowException() {

        saleItemMessageMapper = new SaleItemMessageMapper();

        String[] messages = new String[] {
                "1-10",
                "2-30",
                "3-40"
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            saleItemMessageMapper.map(messages);
        });

        assertTrue(exception instanceof ArrayIndexOutOfBoundsException);

    }
}