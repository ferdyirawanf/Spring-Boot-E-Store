package com.example.estore.utils.specification;
import com.example.estore.DTO.request.ProductDTO;
import com.example.estore.model.Product;
import org.springframework.data.jpa.domain.Specification;
import java.util.function.Predicate;

public class ProductSpecification {
    public static Specification<Product> withName(String productName) {
        return ((root, query, criteriaBuilder) ->
                productName == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + productName.toLowerCase() + "%"));
    }

    public static Specification<Product> withMinPrice(Double minPrice) {
        return ((root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
    }

    public static Specification<Product> withMaxPrice(Double maxPrice) {
        return ((root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
    }

    public static Specification<Product> withCategoryId(Integer categoryId) {
        return ((root, query, criteriaBuilder) ->
                categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("id"), categoryId));
    }

    public static Specification<Product> withCriteria(ProductDTO.Search criteria) {
        return Specification.where(withName(criteria.getName()))
                .and(withMinPrice(criteria.getMinPrice()))
                .and(withMaxPrice(criteria.getMaxPrice()))
                .and(withCategoryId(criteria.getCategoryId()));
    }

    // Predicate to determine if a specification should be applied
    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<?> predicate) {
        return (root, query, criteriaBuilder) -> predicate.test(null) ? spec.toPredicate(root, query, criteriaBuilder): null;
    }
}
