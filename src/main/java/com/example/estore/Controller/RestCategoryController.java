package com.example.estore.Controller;
import com.example.estore.DTO.request.CategoryDTO;
import com.example.estore.DTO.response.RenderJson;
import com.example.estore.service.RestCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class RestCategoryController {
    private final RestCategoryService restCategoryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryRequest) {
        return RenderJson.basicFormat(restCategoryService.create(categoryRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return RenderJson.basicFormat(restCategoryService.index(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return RenderJson.basicFormat(restCategoryService.show(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CategoryDTO categoryRequest) {
        return RenderJson.basicFormat(restCategoryService.update(id, categoryRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id) {
        String response = restCategoryService.delete(id);
        return RenderJson.basicFormat(response, HttpStatus.OK);
    }
}
