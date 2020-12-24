package com.alissonvisa.salesmanapi.domain;


import java.util.Comparator;
import java.util.List;

public class SalesmanRanking {

    private List<SalesmanRankingPosition> positions;

    private SalesmanRanking() {}

    public SalesmanRanking(List<SalesmanRankingPosition> positions) {
        this.positions = positions;
    }

    public String getWorstSalesman() {
        return positions.stream()
                .min(Comparator.comparing(SalesmanRankingPosition::getTotalValueSold))
                .map(it->it.getSalesmanName())
                .orElse("");
    }

}
