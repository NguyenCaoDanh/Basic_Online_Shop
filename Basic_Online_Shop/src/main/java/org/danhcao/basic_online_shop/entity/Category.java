package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "category")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_category", nullable = false)
    private int idCategory;
    @Basic
    @Column(name = "category_name", nullable = false, length = 225)
    private String categoryName;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

}
