package com.alissonvisa.salesmanapi.application.jms;

import com.alissonvisa.salesmanapi.domain.Salesman;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SalesmanMessageMapperTest {

    SalesmanMessageMapper salesmanMessageMapper;

    @Test
    void map() {

        salesmanMessageMapper = new SalesmanMessageMapper();

        String message = "001ç1234567891234çPedro Gonçaloç50000çARCHIVE_01.DAT";

        assertEquals(salesmanMessageMapper.map(message), new Salesman(
                "ARCHIVE_01.DAT",
                "Pedro Gonçalo",
                "1234567891234",
                new BigDecimal(50000)
        ));

    }

    @Test
    void map_InvalidLine_ThrowException() {

        salesmanMessageMapper = new SalesmanMessageMapper();

        String message = "001ç1234567891234çPedro Gonçaloç50000";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            salesmanMessageMapper.map(message);
        });

        assertTrue(exception instanceof IndexOutOfBoundsException);

    }
}