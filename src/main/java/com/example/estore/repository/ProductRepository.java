package com.example.estore.repository;
import com.example.estore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
public interface ProductRepository extends BaseRepository<Product, Integer> {
    // Method Queries
//    List<Product> findByCategoryId(Integer id); // ORM
    List<Product> findByPriceBetween(Double lower, Double higher);
    List<Product> findByNameContainingIgnoreCase(String name);

    // Manual JPQL Query
    @Query("SELECT p FROM Product p WHERE p.price > :price ORDER by p.name") // Product -> Model name and :{sesuatu} = variabel sesuatu
    List<Product> findOrderPriceWithTreshold(@Param("price") Double price);

    // Native Query
    @Query(value = "SELECT p.* FROM products p JOIN categories c ON p.category_id = c.id" // p.* for get all column from table p only
        + "WHERE LOWER(c.name)"
        + "LIKE LOWER(CONCAT('%', :categoryName, '%'))",
            nativeQuery = true
    )
    List<Product> findProductByCategoryName(@Param("categoryName") String categoryName);
}