package com.example.estore.DTO.request;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CategoryDTO {
    private Integer id;
    private String name;
}
