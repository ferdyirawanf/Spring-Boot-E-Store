package com.example.estore.service;

import com.example.estore.DTO.request.TransactionDTO;
import com.example.estore.model.Transaction;
import com.example.estore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface TransactionService {
    Page<TransactionDTO.TransactionBasicFormat> index(Pageable pageable);
    TransactionDTO.TransactionBasicFormat create(TransactionDTO.CreateTransaction transaction, User user);
}
