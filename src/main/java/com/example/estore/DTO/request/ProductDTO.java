package com.example.estore.DTO.request;

import com.example.estore.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductDTO {
    private String name;
    private Double price;

    @JsonProperty("category_id") // yang tampil di json sebagai key request
    private Integer categoryId;

    public static ProductDTO from(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
//                .categoryId(product.getCategory().getId())
                .build();
    }

    @Data
    @Builder
    public static class Search {
        private String name;
        @JsonProperty("min_price")
        private Double minPrice;
        @JsonProperty("max_price")
        private Double maxPrice;
        @JsonProperty("category_id")
        private Integer categoryId;
    }

    @Data
    @Builder
    public static class Response {
        private Integer id;
        private String name;
        private Double price;
        private Integer category_id;

        public static Response from(Product product) {
            return Response.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .category_id(product.getCategory_id()) // kurang id
                    .build();
        }
    }
}
