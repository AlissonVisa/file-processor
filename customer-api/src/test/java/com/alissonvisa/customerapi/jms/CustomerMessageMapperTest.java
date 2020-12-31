package com.alissonvisa.customerapi.jms;

import com.alissonvisa.customerapi.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMessageMapperTest {

    CustomerMessageMapper customerMessageMapper;

    @Test
    void map() {

        customerMessageMapper = new CustomerMessageMapper();

        String message = "002ç2345675434544345çJose GonçalvesçRuralçARCHIVE_01.DAT";

        assertEquals(customerMessageMapper.map(message), new Customer(
                "2345675434544345",
                "Jose Gonçalves",
                "Rural",
                "ARCHIVE_01.DAT"
        ));

    }

    @Test
    void map_InvalidLine_ThrowException() {

        customerMessageMapper = new CustomerMessageMapper();

        String invalidMessage = "002ç2345675434544345çJose GonçalvesçRural";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerMessageMapper.map(invalidMessage);
        });

        assertTrue(exception instanceof IndexOutOfBoundsException);

    }
}