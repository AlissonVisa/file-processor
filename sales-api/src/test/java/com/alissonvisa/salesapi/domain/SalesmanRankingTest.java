package com.alissonvisa.salesapi.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class SalesmanRankingTest {

    @Test
    void getWorstSalesman() {

        List<SalesmanRankingPosition> joaoRanking = List.of(new SalesmanRankingPosition("joao", List.of(
                new Sale(1l, "joao", BigDecimal.valueOf(100l), "ARCHIVE_01.DAT"),
                new Sale(2l, "joao", BigDecimal.valueOf(100l), "ARCHIVE_01.DAT")
        )));

        List<SalesmanRankingPosition> pedroRanking = List.of(new SalesmanRankingPosition("pedro", List.of(
                new Sale(3l, "pedro", BigDecimal.valueOf(25l), "ARCHIVE_01.DAT"),
                new Sale(4l, "pedro", BigDecimal.valueOf(200l), "ARCHIVE_01.DAT")
        )));

        List<SalesmanRankingPosition> allPositions = new ArrayList<>();
        allPositions.addAll(joaoRanking);
        allPositions.addAll(pedroRanking);

        SalesmanRanking salesmanRanking = new SalesmanRanking(allPositions);

        assertEquals(salesmanRanking.getWorstSalesman(), "joao");

    }
}