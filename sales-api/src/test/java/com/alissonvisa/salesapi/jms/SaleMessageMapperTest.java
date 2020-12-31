package com.alissonvisa.salesapi.jms;

import com.alissonvisa.salesapi.domain.Sale;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SaleMessageMapperTest {

    SaleMessageMapper saleMessageMapper;

    @Test
    void map() {

        saleMessageMapper = new SaleMessageMapper();

        String message = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro GonçaloçARCHIVE_01.DAT";

        assertEquals(saleMessageMapper.map(message), new Sale(
                10l, "Pedro Gonçalo", new BigDecimal("1199.00"), "ARCHIVE_01.DAT"
        ));

    }

    @Test
    void map_InvalidLine_ThrowException() {

        saleMessageMapper = new SaleMessageMapper();

        String message = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro Gonçalo";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            saleMessageMapper.map(message);
        });

        assertTrue(exception instanceof IndexOutOfBoundsException);

    }
}