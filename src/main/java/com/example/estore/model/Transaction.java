package com.example.estore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "total_amount")
    private Double totalAmount;

    // Lombok immutable
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionDetail> detailsTransaction = new ArrayList<>();

    public void addTransactionDetail(TransactionDetail detail) {
        detailsTransaction.add(detail);
        detail.setTransaction(this);
    }

    public void removeTransactionDetail(TransactionDetail detail) {
        detailsTransaction.remove(detail);
        detail.setTransaction(null);
    }
}
