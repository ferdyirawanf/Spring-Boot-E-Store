package com.example.estore.service;
import com.example.estore.DTO.request.ProductDTO;
import com.example.estore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product create(ProductDTO product);
    Page<ProductDTO.Response> index(ProductDTO.Search params, Pageable pageable);
//    List<Product> getByCategoryId(Integer categoryId); // TODO     List<Product> getByPriceBetween(Double min, Double max);
    List<Product> getByNameContaining(String name);
    Product show(Integer id);
    Product update(Integer id, ProductDTO product);
    void delete(Integer id);
}
