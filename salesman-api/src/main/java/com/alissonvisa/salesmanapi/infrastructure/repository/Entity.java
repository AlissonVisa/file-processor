package com.alissonvisa.salesmanapi.infrastructure.repository;

public interface Entity<T> {
    T toDomain();
}
