package com.ecommerce.ecommerce.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.common.PageResponse;
import com.ecommerce.ecommerce.product.dto.ProductDisplay;
import com.ecommerce.ecommerce.product.dto.ProductShowcase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponse<ProductShowcase>> getAllProducts(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDisplay> findById(
            @PathVariable(name = "id") Integer id
    ){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/{category}")
    public ResponseEntity<PageResponse<ProductShowcase>> getProductsByCategory(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size,
        @PathVariable(name = "category") String category 
    ){
        return ResponseEntity.ok(productService.getProductsByCategory(page,size,category));
    }

}
