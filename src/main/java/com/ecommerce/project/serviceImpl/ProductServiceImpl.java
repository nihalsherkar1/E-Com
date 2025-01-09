package com.ecommerce.project.serviceImpl;

import com.ecommerce.project.dto.ProductDto;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.response.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ProductDto addProduct(Long categoryid, Product product) {
       Category category= categoryRepository.findById(categoryid).orElseThrow(()->
               new ResourceNotFoundException("Category","categoryId",categoryid));

       product.setImage("Default.png");
        product.setCategory(category);
        double specialPrice= product.getPrice()-(product.getDiscount() * 0.01 * product.getPrice()   );
      product.setSpecialPrice(specialPrice);
         Product savedProduct=  productRepository.save(product);
        return  modelMapper.map(savedProduct,ProductDto.class);
    }


    @Override
    public ProductResponse getAllProduct() {

         List<Product>products=   productRepository.findAll();
         List<ProductDto> productDtos= products.stream()
                 .map(product-> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());

         ProductResponse productResponse= new ProductResponse();
         productResponse.setContent(productDtos);
        return  productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
         Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product>products=   productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDto> productDtos= products.stream()
                .map(product-> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());

        ProductResponse productResponse= new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {


        List<Product>products=   productRepository.findByProductNameLikeIgnoreCase( '%'+ keyword+ '%');
        List<ProductDto> productDtos= products.stream()
                .map(product-> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());

        ProductResponse productResponse= new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }
}
