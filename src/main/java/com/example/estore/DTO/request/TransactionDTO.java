package com.example.estore.DTO.request;

import com.example.estore.model.Transaction;
import com.example.estore.model.TransactionDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDTO {
    @Data
    @Builder
    public static class TransactionBasicFormat {
        private Integer id;
        private Double totalAmount;
        private LocalDateTime createdAt;
        private List<TransactionDetailBasicFormat> details;

        public static TransactionBasicFormat from(Transaction transaction) {
            return TransactionBasicFormat.builder()
                    .id(transaction.getId())
                    .totalAmount(transaction.getTotalAmount())
                    .details(TransactionDetailBasicFormat.mapper(transaction.getDetailsTransaction()))
                    .createdAt(transaction.getCreatedAt())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTransaction {
        @JsonProperty("transaction_detail")
        private List<CreateTransactionDetail> transactionDetail;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTransactionDetail {
        @JsonProperty("product_id")
        private Integer productId;
        private Integer quantity;
    }

    @Data
    @Builder
    public static class TransactionDetailBasicFormat {
        private ProductDTO product;
        private Integer quantity;

        public static TransactionDetailBasicFormat from(TransactionDetail transactionDetail) {
            return TransactionDetailBasicFormat.builder()
                    .product(ProductDTO.from(transactionDetail.getProduct()))
                    .quantity(transactionDetail.getQuantity())
                    .build();
        }

        public static List<TransactionDetailBasicFormat> mapper(List<TransactionDetail> transactionDetails) {
            return transactionDetails.stream()
                    .map(TransactionDetailBasicFormat::from)
                    .collect(Collectors.toList());
        }
    }
}
