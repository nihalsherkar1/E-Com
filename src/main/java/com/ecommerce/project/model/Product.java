package com.ecommerce.project.model;

import jakarta.persistence.*;
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
    private String productName;
    private  String image;
    private String description;
    private  Integer quantity;
    private double price;  // 100
    private double discount; // 25% ===> 25/100
    private double specialPrice; // 100- (0.01*25*100)


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
