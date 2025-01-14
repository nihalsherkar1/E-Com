package com.ecommerce.project.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResourceNotFoundException extends  RuntimeException {
    String resourceName;
    String fieldName;
    Long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
