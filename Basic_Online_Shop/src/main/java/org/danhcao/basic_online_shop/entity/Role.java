package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "role")
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @Basic
    @Column(name = "role_name", nullable = false, length = 45)
    private String roleName;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Account> accounts;

}
