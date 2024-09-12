package com.example.estore.Controller;
import com.example.estore.DTO.request.ProductDTO;
import com.example.estore.DTO.response.RenderJson;
import com.example.estore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO product) {
        return RenderJson.basicFormat(productService.create(product), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "min_price", required = false) Double minPrice,
            @RequestParam(name = "max_price", required = false) Double maxPrice,
            @RequestParam(name = "category_id", required = false) Integer category_id,
            @PageableDefault Pageable pageable)
    {
        ProductDTO.Search params = ProductDTO.Search.builder()
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .categoryId(category_id)
                .build();

        return RenderJson.pageFormat(productService.index(params, pageable), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return RenderJson.basicFormat(productService.show(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProductDTO product) {
        return RenderJson.basicFormat(productService.update(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        productService.delete(id);
        return RenderJson.basicFormat("Successful delete product id : " + id, HttpStatus.OK);
    }
}
