package com.ecommerce.project.controller;


import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.dto.CategoryDto;
import com.ecommerce.project.response.CategoryResponse;

import com.ecommerce.project.serviceImpl.CategoryServiceImpl;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class CategoryController {


    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategory(@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
                                                            @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
                                                           @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_CATEGORIES_BY,required = false) String sortBy,
                                                           @RequestParam(name = "sortOrder",defaultValue = AppConstant.SORT_CATEGORIES_ORDER,required = false) String sortOrder

    ){
        CategoryResponse data=  categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortOrder);

        return  new ResponseEntity<>(data,HttpStatus.OK);

    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> createCategory( @Valid @RequestBody CategoryDto categoryDto){
       String data= categoryService.createCategory(categoryDto);

       if(data.isEmpty()){
           return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }else{
           return new ResponseEntity<>(data,HttpStatus.CREATED);
       }


    }

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        String status=  categoryService.deleteCategory(id);

        if (status == "Category not found" ){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(status,HttpStatus.OK);
        }


    }


    @PutMapping("/admin/category/{id}")
    public ResponseEntity<CategoryDto> updateCategory( @RequestBody CategoryDto category, @PathVariable Long id){
        CategoryDto data= categoryService.updateCategory(category,id);

        if(data==null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(data,HttpStatus.OK);
        }



    }

}
