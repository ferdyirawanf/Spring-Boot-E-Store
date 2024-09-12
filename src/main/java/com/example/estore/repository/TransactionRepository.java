package com.example.estore.repository;

import com.example.estore.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Integer> {
}
