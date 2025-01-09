package com.ecommerce.project.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CategoryDto {

    private Long categoryId;

    @NotBlank(message ="Category name should not be blank!" )
    @Size(min=5,message = "Character must be minimum 5 characters")
    private String categoryName;

}
