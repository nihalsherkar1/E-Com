package com.ecommerce.project.service;

import com.ecommerce.project.dto.ProductDto;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.response.ProductResponse;

public interface ProductService {

    public ProductDto addProduct(Long categoryid, Product product);
    public ProductResponse getAllProduct();


    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchByKeyword(String keyword);
}
