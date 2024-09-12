package com.example.estore.service.impl;
import com.example.estore.DTO.request.TransactionDTO;
import com.example.estore.model.Product;
import com.example.estore.model.Transaction;
import com.example.estore.model.TransactionDetail;
import com.example.estore.model.User;
import com.example.estore.repository.TransactionRepository;
import com.example.estore.service.ProductService;
import com.example.estore.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductService productService;
    private final EmailService emailService;

    @Override
    public Page<TransactionDTO.TransactionBasicFormat> index(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(TransactionDTO.TransactionBasicFormat::from);
    }

    @Override
    public TransactionDTO.TransactionBasicFormat create(TransactionDTO.CreateTransaction transaction, User user) {
        Transaction trans = new Transaction();
        trans.setUser(user);
        Double totalAmount = 0.0;

        for (TransactionDTO.CreateTransactionDetail detail : transaction.getTransactionDetail()) {
            TransactionDetail transactionDetail = createDetail(detail);
            trans.addTransactionDetail(transactionDetail);
            totalAmount += (transactionDetail.getProduct().getPrice() * transactionDetail.getQuantity());
        }

        trans.setTotalAmount(totalAmount);
        Transaction savedTransaction = transactionRepository.save(trans);
        emailService.sendTransactionEmailConfirmation(user.getUsername(), String.valueOf(totalAmount));
        return TransactionDTO.TransactionBasicFormat.from(savedTransaction);
    }

    private TransactionDetail createDetail(TransactionDTO.CreateTransactionDetail detail) {
        Product product = productService.show(detail.getProductId());
        return TransactionDetail.builder()
                .product(product)
                .quantity(detail.getQuantity())
                .build();

    }
}
