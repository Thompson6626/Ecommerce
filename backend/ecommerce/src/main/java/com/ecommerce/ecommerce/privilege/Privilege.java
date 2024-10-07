package com.ecommerce.ecommerce.privilege;


import com.ecommerce.ecommerce.role.Role;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Privilege  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges",fetch = FetchType.EAGER)
    private List<Role> roles;
    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles.stream().map(Role::getName).collect(Collectors.toList()) + 
                '}';
    }
}
