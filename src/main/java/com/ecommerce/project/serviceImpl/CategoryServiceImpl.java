package com.ecommerce.project.serviceImpl;



import com.ecommerce.project.dto.CategoryDto;
import com.ecommerce.project.exceptions.ApiException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.response.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    public CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder){
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc") ?  Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageDetail= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page categoryPage= categoryRepository.findAll(pageDetail);

       List<Category> categories= categoryPage.getContent();

       if(categories.isEmpty()){
           throw  new ApiException("Categories are not found...");
       }

       List<CategoryDto> categoryDtos =  categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        CategoryResponse categoryResponse=new CategoryResponse();

        categoryResponse.setContents(categoryDtos);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;



    }



    public String createCategory( CategoryDto categoryDto){
        Category category1=modelMapper.map(categoryDto,Category.class);

        Category savedCategory=categoryRepository.findByCategoryName(category1.getCategoryName());

        if (savedCategory != null){
            throw new ApiException("Category with the name "+categoryDto.getCategoryName()+" is already exist !!");
        }
        Category saved=  categoryRepository.save(category1);
        modelMapper.map(saved,CategoryDto.class);
        return "Category added Successfully";
    }

    @Override

    public String deleteCategory(Long id) {

       Category existingData  =categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",id));

        if(existingData == null){
            return "Category not found";
        }else{
            categoryRepository.deleteById(id);
            return "Category with categoryId: "+id+ " deleted successfully";
        }

    }

    @Override
    public CategoryDto updateCategory(CategoryDto newCategory, Long id) {

        Category existingData  =categoryRepository.findById(id).orElse(null);

        if (existingData != null){
            existingData.setCategoryName(newCategory.getCategoryName()); ;
           Category saved=    categoryRepository.save(existingData);
            return modelMapper.map(saved,CategoryDto.class);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found..");
        }


    }
}
