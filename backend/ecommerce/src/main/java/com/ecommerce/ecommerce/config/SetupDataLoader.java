package com.ecommerce.ecommerce.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.permission.Privilege;
import com.ecommerce.ecommerce.permission.PrivilegeRepository;
import com.ecommerce.ecommerce.role.Role;
import com.ecommerce.ecommerce.role.RoleRepository;
import com.ecommerce.ecommerce.user.User;
import com.ecommerce.ecommerce.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;
 
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
        User adminUser = new User();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setEmail("admin@example.com");
        adminUser.setRoles(Arrays.asList(adminRole));
        adminUser.setEnabled(true);
        userRepository.save(adminUser);

        // Create a buyer user for testing
        User buyerUser = new User();
        buyerUser.setFirstName("Buyer");
        buyerUser.setLastName("User");
        buyerUser.setPassword(passwordEncoder.encode("buyer"));
        buyerUser.setEmail("buyer@example.com");
        buyerUser.setRoles(Arrays.asList(buyerRole));
        buyerUser.setEnabled(true);
        userRepository.save(buyerUser);

        // Create a seller user for testing
        User sellerUser = new User();
        sellerUser.setFirstName("Seller");
        sellerUser.setLastName("User");
        sellerUser.setPassword(passwordEncoder.encode("seller"));
        sellerUser.setEmail("seller@example.com");
        sellerUser.setRoles(Arrays.asList(sellerRole));
        sellerUser.setEnabled(true);
        userRepository.save(sellerUser);

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name)
                                    .orElseGet(() -> {
                                        var priv = new Privilege();
                                        priv.setName(name);
                                        return privilegeRepository.save(priv);
                                    });
    }

    @Transactional
    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        return roleRepository.findByName(name)
                                .orElseGet(() -> {
                                    var newRole = new Role();
                                    newRole.setName(name);
                                    newRole.setPrivileges(privileges);
                                    return roleRepository.save(newRole);
                                });
    }
}
