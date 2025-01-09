package com.ecommerce.project.service;

import com.ecommerce.project.dto.CategoryDto;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.response.CategoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


public interface CategoryService {

    public CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);


    public String createCategory(CategoryDto categoryDto);

    public String deleteCategory(Long id);

    public CategoryDto updateCategory(CategoryDto newCategory,Long id);





}
