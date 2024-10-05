package com.ecommerce.ecommerce.product;

import com.ecommerce.ecommerce.product.dto.ProductRequest;
import com.ecommerce.ecommerce.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.common.PageResponse;
import com.ecommerce.ecommerce.product.dto.ProductDisplay;
import com.ecommerce.ecommerce.product.dto.ProductShowcase;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponse<ProductShowcase>> getAllProducts(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page,size));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDisplay> findById(
            @PathVariable(name = "id") Integer id
    ){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<PageResponse<ProductShowcase>> getProductsByCategory(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size,
        @PathVariable(name = "category") @NotBlank(message = "Category must not be blank") String category
    ){
        return ResponseEntity.ok(productService.getProductsByCategory(page,size,category));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PageResponse<ProductShowcase>> getProductsByStatus(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @PathVariable(name = "status") @NotBlank(message = "Status must not be blank") String status
    ){
        return ResponseEntity.ok(productService.getProductsByStatus(page,size,status));
    }

    @GetMapping("/s")
    public ResponseEntity<PageResponse<ProductShowcase>> getAllByPrefix(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "prefix") @NotBlank(message = "Search term must not be blank") String prefix
    ){
        return ResponseEntity.ok(productService.getAllByPrefix(page,size,prefix));
    }

    @PostMapping
    public ResponseEntity<ProductShowcase> requestProductListing(
            @Valid @RequestBody ProductRequest request,
            @AuthenticationPrincipal User user
    ) {
        var createdProduct = productService.requestProductListing(request,user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id/{id}")
                .buildAndExpand(createdProduct.id())
                .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ProductShowcase> modifyProductById(
            @PathVariable(name = "id") int productId,
            @RequestBody ProductRequest updateRequest, // Omitting @Valid on purpose
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(productService.modifyProductById(productId,updateRequest,user));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable(name = "id") int productId,
            @AuthenticationPrincipal User user
    ){
        productService.deleteById(productId,user);
        return ResponseEntity.ok().build();
    }

}
