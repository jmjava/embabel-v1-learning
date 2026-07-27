package com.embabel.learning.common.service;

import com.embabel.learning.common.domain.BankCustomer;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tiny in-memory customer store for the domain-tools lesson.
 * Demonstrates that Embabel agents are normal Spring-managed classes.
 */
@Repository
public class InMemoryCustomerRepository {

    private final Map<Long, BankCustomer> customers = new ConcurrentHashMap<>();

    public InMemoryCustomerRepository() {
        customers.put(1L, new BankCustomer(1L, "Ada Lovelace", 1200.0f, 75.0f));
        customers.put(2L, new BankCustomer(2L, "Grace Hopper", 80.0f, 140.0f));
    }

    public BankCustomer findById(Long id) {
        return customers.get(id);
    }
}
