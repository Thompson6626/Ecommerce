package com.ecommerce.ecommerce.product;

import com.ecommerce.ecommerce.common.PageResponse;
import com.ecommerce.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.ecommerce.exceptions.Identifier;
import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.ecommerce.exceptions.UnauthorizedModificationException;
import com.ecommerce.ecommerce.product.category.Category;
import com.ecommerce.ecommerce.product.category.CategoryRepository;
import com.ecommerce.ecommerce.product.dto.ProductDisplay;
import com.ecommerce.ecommerce.product.dto.ProductRequest;
import com.ecommerce.ecommerce.product.dto.ProductShowcase;
import com.ecommerce.ecommerce.user.User;
import com.ecommerce.ecommerce.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public PageResponse<ProductShowcase> getAllProducts(int page, int size) {
        Pageable pageable = Utils.pageableSortedBy(page,size,"rating",false);

        Page<Product> products = productRepository.findByStatus(
                ProductStatus.APPROVED,
                pageable
        );
        return Utils.generatePageResponse(products,productMapper::toShowCase);
    }

    public ProductDisplay getProductById(int id){
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(Identifier.ID,id));

        return productMapper.toDisplay(product);
    }

    public PageResponse<ProductShowcase> getProductsByCategory(int page, int size, String category){
        Pageable pageable = Utils.pageableSortedBy(page,size,"rating",false);
        Category cat = categoryRepository.findByName(category)
                        .orElseThrow(() -> new CategoryNotFoundException(Identifier.CATEGORY,category));

        Specification<Product> spec = Specification.where(ProductSpecifications.hasCategory(cat))
                .and(ProductSpecifications.hasStatus(ProductStatus.APPROVED));

        Page<Product> products = productRepository.findAll(spec,pageable);

        return Utils.generatePageResponse(products, productMapper::toShowCase);
    }

    public PageResponse<ProductShowcase> getAllByPrefix(
            int page,
            int size,
            String prefix
    ) {
        Pageable pageable = Utils.pageableSortedBy(page,size,"rating",false);
        Specification<Product> spec = Specification
                .where(ProductSpecifications.hasStatus(ProductStatus.APPROVED))
                .and(ProductSpecifications.nameStartsWith(prefix));

        Page<Product> products = productRepository.findAll(spec,pageable);
        return Utils.generatePageResponse(products,productMapper::toShowCase);
    }

    public ProductShowcase requestProductListing(ProductRequest request, User user) {
        Product product = productMapper.toProduct(request);

        Category category = categoryRepository.findByName(request.category())
                .orElseThrow(() -> new CategoryNotFoundException(Identifier.NAME,request.category()));

        product.setCategory(category);
        product.setSeller(user);
        product = productRepository.save(product);

        return productMapper.toShowCase(product);
    }

    public PageResponse<ProductShowcase> getProductsByStatus(int page, int size, String st) {
        ProductStatus status = ProductStatus.from(st);

        Pageable pageable = Utils.pageableSortedBy(page,size,"rating",false);

        Page<Product> products = productRepository.findByStatus(
                status,
                pageable
        );
        return Utils.generatePageResponse(products,productMapper::toShowCase);
    }

    public ProductShowcase modifyProductById(
            int productId,
            ProductRequest updateRequest,
            User user
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(Identifier.ID,productId));

        if (!product.getId().equals(user.getId())){
            throw new UnauthorizedModificationException();
        }

        String cat = updateRequest.category();
        if (cat != null){
            Category category = categoryRepository.findByName(cat)
                    .orElseThrow(() -> new CategoryNotFoundException(Identifier.NAME,cat));
            product.setCategory(category);
        }

        if (updateRequest.description() != null && !updateRequest.description().isBlank()){
            product.setDescription(updateRequest.description());
        }
        if (updateRequest.name() != null && !updateRequest.name().isBlank()){
            product.setName(updateRequest.name());
        }
        if (updateRequest.price() != null && updateRequest.price().compareTo(BigDecimal.ZERO) >= 0){
            product.setPrice(updateRequest.price());
        }
        if (updateRequest.imageUrl() != null && !updateRequest.imageUrl().isBlank()){
            product.setImageUrl(updateRequest.imageUrl());
        }
        if(updateRequest.stock() != null && updateRequest.stock().compareTo(0) >= 0){
            product.setStock(updateRequest.stock());
        }

        return productMapper.toShowCase(productRepository.save(product));
    }

    public void deleteById(int productId, User user) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(Identifier.ID,productId));

        if (!product.getSeller().getId().equals(user.getId()) &&
            user.getRoles().stream().noneMatch(role -> role.getName().equals("ROLE_ADMIN"))
        ){
            throw new UnauthorizedModificationException();
        }

        productRepository.deleteById(productId);
    }
}
