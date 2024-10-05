package com.ecommerce.ecommerce.product.category;

import com.ecommerce.ecommerce.product.category.dto.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryDTO toDTO(Category category){
        return CategoryDTO.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category toCategory(CategoryDTO request) {
        return Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }
}
