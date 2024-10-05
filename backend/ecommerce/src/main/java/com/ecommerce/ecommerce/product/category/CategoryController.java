package com.ecommerce.ecommerce.product.category;

import com.ecommerce.ecommerce.product.category.dto.CategoryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            @Valid @RequestBody CategoryDTO request
            ){
        return ResponseEntity.ok(categoryService.createCategory(request));
    }
    @PutMapping("/{categoryName}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @RequestBody CategoryDTO request,    // Omitting @Valid annotation on purpose
            @PathVariable("categoryName") String categoryName
    ){
        return ResponseEntity.ok(categoryService.updateCategory(categoryName,request));
    }
    @DeleteMapping("/{categoryName}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable("categoryName") String categoryName
    ){
        categoryService.deleteCategory(categoryName);
        return ResponseEntity.ok().build();
    }
}
