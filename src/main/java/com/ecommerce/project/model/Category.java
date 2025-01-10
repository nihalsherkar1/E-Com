package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.List;


@Entity(name = "categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message ="Category name should not be blank!" )
    @Size(min=5,message = "Character must be minimum 5 characters")
    private String categoryName;


    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> product;


}
