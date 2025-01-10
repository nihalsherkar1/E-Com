package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.dto.CategoryDto;
import com.ecommerce.project.dto.ProductDto;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.response.ProductResponse;
import com.ecommerce.project.serviceImpl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(@Valid @PathVariable Long categoryId, @RequestBody ProductDto productDto){
        ProductDto savedProductDto= productService.addProduct(categoryId,productDto);
        return  new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProduct(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber  ,
            @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCT_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstant.SORT_PRODUCT_ORDER,required = false)String sortOrder
    ){
       ProductResponse productResponse= productService.getAllProduct(pageNumber,pageSize,sortBy,sortOrder);

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

    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid   @PathVariable Long productId,@RequestBody ProductDto productDto){
       ProductDto productData=   productService.updateProduct(productId,productDto);
     return  new ResponseEntity<>(productData,HttpStatus.OK);
    }

    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
         String data=  productService.deleteProduct(productId);
         return new ResponseEntity<>(data  ,HttpStatus.OK);
    }


    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDto>updateProductImage(@PathVariable Long productId,
                                                        @RequestParam("image")MultipartFile image) throws IOException {
      ProductDto UpdatedProductDto=   productService.updateProductImage(productId,image);
      return  new ResponseEntity<>(UpdatedProductDto,HttpStatus.OK);

    }


}
