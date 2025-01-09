package com.ecommerce.project.response;

import com.ecommerce.project.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDto> contents;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private  Integer totalPages;
    private boolean lastPage;



}
