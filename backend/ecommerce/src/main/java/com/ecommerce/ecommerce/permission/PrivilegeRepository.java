package com.ecommerce.ecommerce.permission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege,Integer>{
    
    Optional<Privilege> findByName(String name);
}
