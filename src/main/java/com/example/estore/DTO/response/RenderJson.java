package com.example.estore.DTO.response;
import com.example.estore.DTO.response.format.BasicFormat;
import com.example.estore.DTO.response.format.PageFormat;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RenderJson {
    public static <T> ResponseEntity<?> basicFormat(T data, HttpStatus httpStatus) {
        BasicFormat<T> response = BasicFormat.<T>builder()
                .data(data)
                .status(httpStatus)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<?> pageFormat(Page<T> page, HttpStatus httpStatus) {
        PageFormat<T> pageFormat = PageFormat.<T>builder()
                .content(page.getContent())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .build();

        BasicFormat<PageFormat<T>> response = BasicFormat.<PageFormat<T>>builder()
                .data(pageFormat)
                .status(httpStatus)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }
}
