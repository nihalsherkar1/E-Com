package com.ecommerce.project.controller;

import com.ecommerce.project.dto.CategoryDto;
import com.ecommerce.project.dto.ProductDto;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.response.ProductResponse;
import com.ecommerce.project.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct( @PathVariable Long categoryId,@RequestBody Product product){
        ProductDto productDto= productService.addProduct(categoryId,product);
        return  new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProduct(){
       ProductResponse productResponse= productService.getAllProduct();

       return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/category/{categoryId}/products")
    public ResponseEntity<ProductResponse>getProductByCategory(@PathVariable Long categoryId ){
       ProductResponse productResponse=   productService.searchByCategory(categoryId);
       return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword){
        ProductResponse product=  productService.searchByKeyword(keyword);
     return  new ResponseEntity<>(product,HttpStatus.FOUND);
    }


}
