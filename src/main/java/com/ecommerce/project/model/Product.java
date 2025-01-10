package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 3, message = "Product name must be contain atleast 3 characters")
    private String productName;
    private  String image;
    @NotBlank
    @Size(min = 6, message = "Product name must be contain atleast 6 characters")
    private String description;
    private  Integer quantity;
    private double price;  // 100
    private double discount; // 25% ===> 25/100
    private double specialPrice; // 100- (0.01*25*100)


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
