package com.ecommerce.ecommerce.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.ecommerce.ecommerce.product.Product;
import com.ecommerce.ecommerce.product.ProductRepository;
import com.ecommerce.ecommerce.product.ProductStatus;
import com.ecommerce.ecommerce.product.category.Category;
import com.ecommerce.ecommerce.product.category.CategoryRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.privilege.Privilege;
import com.ecommerce.ecommerce.privilege.PrivilegeRepository;
import com.ecommerce.ecommerce.role.Role;
import com.ecommerce.ecommerce.role.RoleRepository;
import com.ecommerce.ecommerce.user.User;
import com.ecommerce.ecommerce.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    

    boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) return;

        // Create privileges for each role
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege buyPrivilege = createPrivilegeIfNotFound("BUY_PRIVILEGE");
        Privilege sellPrivilege = createPrivilegeIfNotFound("SELL_PRIVILEGE");
        Privilege modifyPrivilege = createPrivilegeIfNotFound("MODIFY_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        List<Privilege> buyerPrivileges = Arrays.asList(buyPrivilege,readPrivilege);
        List<Privilege> sellerPrivileges = Arrays.asList(sellPrivilege);
        List<Privilege> adminPrivileges = Arrays.asList(modifyPrivilege,deletePrivilege);


        // Create roles for admin, buyer, and seller
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_BUYER", buyerPrivileges);
        createRoleIfNotFound("ROLE_SELLER", sellerPrivileges);

        // Find or throw exception if the admin role is not set
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin Role not set."));
        
        Role buyerRole = roleRepository.findByName("ROLE_BUYER")
                .orElseThrow(() -> new RuntimeException("Buyer Role not set."));
        
        Role sellerRole = roleRepository.findByName("ROLE_SELLER")
                .orElseThrow(() -> new RuntimeException("Seller Role not set."));

        // Create an admin user for testing
        var adminUser = User.builder()
                .firstName("Admin")
                .lastName("User")
                .password(passwordEncoder.encode("adminnnnnnnnnn"))
                .email("admin@example.com")
                .roles(Arrays.asList(adminRole))
                .enabled(true)
                .build();
        userRepository.save(adminUser);

        // Create a buyer user for testing
        var buyerUser = User.builder()
                .firstName("Buyer")
                .lastName("User")
                .password(passwordEncoder.encode("buyer"))
                .email("buyer@example.com")
                .roles(Arrays.asList(buyerRole))
                .enabled(true)
                .build();
        userRepository.save(buyerUser);

        // Create a seller user for testing
        var sellerUser = User.builder()
                .firstName("Seller")
                .lastName("User")
                .password(passwordEncoder.encode("seller"))
                .roles(Arrays.asList(sellerRole))
                .email("seller@example.com")
                .enabled(true)
                .build();
        userRepository.save(sellerUser);

        var sellerUser2 = User.builder()
                .firstName("Seller2")
                .lastName("User2")
                .password(passwordEncoder.encode("seller2"))
                .roles(Arrays.asList(sellerRole))
                .email("seller2@example.com")
                .enabled(true)
                .build();
        userRepository.save(sellerUser2);

        var sellerUser3 = User.builder()
                .firstName("Seller3")
                .lastName("User3")
                .password(passwordEncoder.encode("seller3"))
                .roles(Arrays.asList(sellerRole))
                .email("seller3@example.com")
                .enabled(true)
                .build();
        userRepository.save(sellerUser3);



        createSampleCategoriesAndProducts(sellerUser,sellerUser3,sellerUser2);

        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name)
                                    .orElseGet(() -> {
                                        var priv = new Privilege();
                                        priv.setName(name);
                                        return privilegeRepository.save(priv);
                                    });
    }

    @Transactional
    public Role createRoleIfNotFound(String name, List<Privilege> privileges) {
        return roleRepository.findByName(name)
                                .orElseGet(() -> {
                                    var newRole = new Role();
                                    newRole.setName(name);
                                    newRole.setPrivileges(privileges);
                                    return roleRepository.save(newRole);
                                });
    }

    private void createSampleCategoriesAndProducts(
            User seller1,User seller2, User seller3
    ) {
        var electronics = Category.builder()
                            .name("Electronics")
                            .description("Devices, gadgets, and technological equipment.")
                            .build();

        var books = Category.builder()
                        .name("Books")
                        .description("A wide variety of books across different genres.")
                        .build();

        var clothing = Category.builder()
                        .name("Clothing")
                        .description("Fashionable clothing for men, women, and children.")
                        .build();

        var home = Category.builder()
                        .name("Home & Kitchen")
                        .description("Essentials and decor for your home and kitchen.")
                        .build();

        var sports = Category.builder()
                        .name("Sports & Outdoors")
                        .description("Equipment and apparel for sports and outdoor activities.")
                        .build();

        var toys = Category.builder()
                        .name("Toys & Games")
                        .description("Fun and educational toys for children of all ages.")
                        .build();

        var health = Category.builder()
                        .name("Health & Beauty")
                        .description("Products for personal care, beauty, and wellness.")
                        .build();
        var automotive =  Category.builder()
                        .name("Automotive")
                        .description("Parts, accessories, and tools for automotive needs.")
                        .build();


        // Save sample categories to the database
        categoryRepository.saveAll(List.of(automotive,health,toys,sports,home,clothing,books,electronics));

        var p1 = Product.builder()
                .name("Product1")
                .seller(null)
                .price(BigDecimal.valueOf(100))
                .description("Descriptionnnnnnnnnnnnnnnnnn")
                .imageUrl("randomurlhere.com")
                .status(ProductStatus.APPROVED)
                .stock(4)
                .seller(seller1)
                .category(books)
                .build();

        var p2 = Product.builder()
                .name("Product2")
                .seller(null)
                .price(BigDecimal.valueOf(200))
                .description("Descriptionnnnnnnnnnnnnnnnnn")
                .imageUrl("randomurlhere2.com")
                .status(ProductStatus.APPROVED)
                .stock(2)
                .seller(seller3)
                .category(electronics)
                .build();

        var p3 = Product.builder()
                .name("Product3")
                .seller(null)
                .price(BigDecimal.valueOf(120))
                .description("Descriptionnnnnnnnnnnnnnnnnn")
                .imageUrl("randomurlhere3.3com")
                .status(ProductStatus.APPROVED)
                .stock(1)
                .seller(seller2)
                .category(health)
                .build();

        productRepository.saveAll(List.of(p1,p2,p3));
    }

}
