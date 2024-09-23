package com.ecommerce.ecommerce;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ecommerce.ecommerce.permission.Permission;
import com.ecommerce.ecommerce.permission.PermissionRepository;
import com.ecommerce.ecommerce.role.Role;
import com.ecommerce.ecommerce.role.RoleRepository;
import com.ecommerce.ecommerce.user.UserRepository;

@SpringBootApplication
public class EcommerceApplication  implements CommandLineRunner{

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	@Override
    public void run(String... args) throws Exception {
		var read_perm = Permission.builder().name("READ_PRIVILEGES").build();
	read_perm = permissionRepository.save(read_perm);

	// Save all other permissions for the ADMIN role
	var write_perm = Permission.builder().name("WRITE_PRIVILEGES").build();
	write_perm = permissionRepository.save(write_perm);

	var delete_perm = Permission.builder().name("DELETE_PRIVILEGES").build();
	delete_perm = permissionRepository.save(delete_perm);

	var moderate_perm = Permission.builder().name("MODERATE_USERS").build();
	moderate_perm = permissionRepository.save(moderate_perm);

	// Save USER role with read permission
	roleRepository.save(
		Role.builder()
			.name("USER")
			.permissions(new HashSet<>(Set.of(read_perm)))
			.build()
	);

	// Save ADMIN role with multiple permissions
	roleRepository.save(
		Role.builder()
			.name("ADMIN")
			.permissions(new HashSet<>(Set.of(
				read_perm, write_perm, delete_perm, moderate_perm
			)))
			.build()
	);

        // Log all users
        System.out.println("=== Users ===");
        userRepository.findAll().forEach(user -> System.out.println(user.getName()));

        // Log all roles
        System.out.println("=== Roles ===");
        roleRepository.findAll().forEach(role -> {
			System.out.println(role.getName());
			role.getPermissions().stream().forEach((r) -> System.out.println(r.getName()));
		});
		
        // Log all permissions
        System.out.println("=== Permissions ===");
        permissionRepository.findAll().forEach(permission -> {
			System.out.println(permission.getName());
			permission.getRoles().stream().forEach((r) -> System.out.println(r.getName()));
		});
    }
}
