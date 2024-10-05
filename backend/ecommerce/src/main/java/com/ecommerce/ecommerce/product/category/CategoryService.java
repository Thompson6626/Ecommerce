package com.ecommerce.ecommerce.product.category;

import com.ecommerce.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.ecommerce.exceptions.Identifier;
import com.ecommerce.ecommerce.product.category.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryDTO> getAllCategories(){
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO createCategory(CategoryDTO request) {
        var category = mapper.toCategory(request);
        category = repository.save(category);

        return mapper.toDTO(category);
    }

    public CategoryDTO updateCategory(String categoryName, CategoryDTO request) {
        var toUpdate = repository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(Identifier.NAME,categoryName));

        if (request.name() != null && request.description().isBlank())
            toUpdate.setName(request.name());

        if (request.description() != null && request.description().isBlank())
            toUpdate.setDescription(request.description());

        return mapper.toDTO(toUpdate);
    }

    public void deleteCategory(String categoryName) {
        var toDelete = repository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(Identifier.NAME,categoryName));

        repository.delete(toDelete);
    }
}
