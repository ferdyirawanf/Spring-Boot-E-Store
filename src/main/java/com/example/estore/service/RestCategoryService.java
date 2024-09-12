package com.example.estore.service;
import com.example.estore.DTO.request.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestCategoryService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8081/api/v1/categories";

    public CategoryDTO create(CategoryDTO categoryRequest) {
        HttpEntity<CategoryDTO> request = new HttpEntity<>(categoryRequest);

        ResponseEntity<CategoryDTO> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    public List<CategoryDTO> index() {
        ResponseEntity<List<CategoryDTO>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CategoryDTO>>() {}
        );

        System.out.println(response.getBody());
        return response.getBody();
    }

    public CategoryDTO show(Integer id) {
        ResponseEntity<CategoryDTO> response = restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CategoryDTO>() {}
        );

        return response.getBody();
    }

    public CategoryDTO update(Integer id, CategoryDTO categoryRequest) {
        if (show(id) == null) { return null; }

        HttpEntity<CategoryDTO> request = new HttpEntity<>(categoryRequest);

        ResponseEntity<CategoryDTO> response = restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        System.out.println(response);

        return response.getBody();
    }

    public String delete(Integer id) {
        HttpEntity<Integer> request = new HttpEntity<>(id);

        System.out.println(request);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        System.out.println(response);
        return response.getBody().toString();
    }
}
