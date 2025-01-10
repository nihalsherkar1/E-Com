package com.ecommerce.project.service;

import com.ecommerce.project.dto.ProductDto;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    public ProductDto addProduct(Long categoryid, ProductDto productDto);

    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy,String sortOrder);


    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchByKeyword(String keyword);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    String deleteProduct(Long productId);

    ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException;
}
