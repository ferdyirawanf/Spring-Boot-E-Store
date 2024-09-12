package com.example.estore.service.impl;
import com.example.estore.DTO.request.ProductDTO;
import com.example.estore.model.Product;
import com.example.estore.repository.ProductRepository;
import com.example.estore.service.ProductService;
import com.example.estore.service.RestCategoryService;
import com.example.estore.utils.specification.NonDiscardedSpecification;
import com.example.estore.utils.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final RestCategoryService categoryService;

    @Override
    public Product create(ProductDTO productRequest) {

        Product newProduct = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .category_id(categoryService.show(productRequest.getCategoryId()).getId())
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Page<ProductDTO.Response> index(ProductDTO.Search params, Pageable pageable) {
        Specification<Product> specification = Specification.where(NonDiscardedSpecification.notDiscarded());

        specification = specification.and(ProductSpecification.specificationFromPredicate(
                ProductSpecification.withName(params.getName()),
                p -> params.getName() != null && !params.getName().isEmpty()
        ));

        specification = specification.and(ProductSpecification.specificationFromPredicate(
                ProductSpecification.withMinPrice(params.getMinPrice()),
                p -> params.getMinPrice() != null
        ));

        specification = specification.and(ProductSpecification.specificationFromPredicate(
                ProductSpecification.withMaxPrice(params.getMaxPrice()),
                p -> params.getMaxPrice() != null
        ));

        specification = specification.and(ProductSpecification.specificationFromPredicate(
                ProductSpecification.withCategoryId(params.getCategoryId()),
                p -> params.getCategoryId() != null
        ));
        
        return productRepository.findAll(specification, pageable).map(ProductDTO.Response::from);
    }

//    @Override
//    public List<Product> getByCategoryId(Integer categoryId) {
//        return productRepository.findByCategoryId(categoryId);
//    }

    @Override
    public List<Product> getByPriceBetween(Double min, Double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Product> getByNameContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Product show(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product update(Integer id, ProductDTO updateRequest) {
        Product product = show(id);

        if (updateRequest.getName() == null || updateRequest.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        }

        if (updateRequest.getPrice() == null || updateRequest.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price cannot be empty and must be positive number");
        }

        if (updateRequest.getCategoryId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category Id cannot be empty");
        }

        product.setName(updateRequest.getName());
        product.setPrice(updateRequest.getPrice());
        product.setCategory_id(categoryService.show(updateRequest.getCategoryId()).getId());

        return productRepository.save(product);
    }

    @Override
    public void delete(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }
}
