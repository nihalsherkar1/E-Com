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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private FileServiceImpl fileServiceImpl;

//    @Autowired
//    private ProductService productService;


    public ProductDto addProduct(Long categoryid, ProductDto productDto) {
        Category category= categoryRepository.findById(categoryid).orElseThrow(()->
               new ResourceNotFoundException("Category","categoryId",categoryid));

        Product product=modelMapper.map(productDto,Product.class);

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

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
      Product productFromDb=  productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

      Product product= modelMapper.map(productDto,Product.class);

      //Now update that data
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
         productFromDb.setPrice(product.getPrice());
        productFromDb.setImage(product.getImage());

        //Calculate Special Price
        double price=product.getPrice();
        double discount=product.getDiscount();
        double specialPrice= price-(price* discount/100);

       productFromDb.setSpecialPrice(specialPrice);

        Product savedProduct= productRepository.save(productFromDb);

        return modelMapper.map(savedProduct,ProductDto.class);
    }


    @Override
    public String deleteProduct(Long productId) {
       Product productPresentInDb=  productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

       if(productPresentInDb != null){
              productRepository.deleteById(productId);
              return "Product with id "+ productId +" Deleted Successfully";
       }else{
           return "Something Went Wrong";
       }


    }

    @Override
    public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {
         // Get Product from db
       Product productFromDb= productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        // upload image on directory/Server
        // get file name of upload image
        String path="images/";

        String fileName= fileServiceImpl.uploadImage(path, image);

        // updating the new file name to the product
        productFromDb.setImage(fileName);

        //save product
        Product savedProduct= productRepository.save(productFromDb);
        // return dto
        return modelMapper.map(savedProduct,ProductDto.class);
    }


}
