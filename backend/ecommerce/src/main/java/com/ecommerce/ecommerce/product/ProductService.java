package com.ecommerce.ecommerce.product;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import com.ecommerce.ecommerce.category.Category;
import com.ecommerce.ecommerce.category.CategoryRepository;
import com.ecommerce.ecommerce.common.PageResponse;
import com.ecommerce.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.ecommerce.exceptions.Identifier;
import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.ecommerce.product.dto.ProductDisplay;
import com.ecommerce.ecommerce.product.dto.ProductShowcase;
import com.ecommerce.ecommerce.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public PageResponse<ProductShowcase> getAllProducts(int page, int size) {
        Pageable pageable = generatePageableSortedByReview(page, size);
        Page<Product> products = productRepository.findAll(pageable);

        return Utils.generatePageResponse(products,productMapper::toShowCase);
    }

    public ProductDisplay getProductById(int id){
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(Identifier.ID,id));

        return productMapper.toDisplay(product);
    }

    public PageResponse<ProductShowcase> getProductsByCategory(int page, int size, String category){
        Pageable pageable = generatePageableSortedByReview(page, size);

        Category cat = categoryRepository.findByName(category)
                        .orElseThrow(() -> new CategoryNotFoundException(Identifier.CATEGORY,category));

        Page<Product> products = productRepository.findByCategory(cat,pageable);

        return Utils.generatePageResponse(products, productMapper::toShowCase);
    }






    private Pageable generatePageableSortedByReview(int page, int size){
        return PageRequest.of(
                page,
                size,
                Sort.by("rating").descending()
        );
    }
}
