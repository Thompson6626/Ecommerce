package com.ecommerce.ecommerce.role;

import com.ecommerce.ecommerce.privilege.Privilege;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.ecommerce.ecommerce.user.User;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, unique=true)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
                name = "role_id",
                referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
                name = "privilege_id",
                referencedColumnName = "id"
        )
    )
    private Collection<Privilege> privileges;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
