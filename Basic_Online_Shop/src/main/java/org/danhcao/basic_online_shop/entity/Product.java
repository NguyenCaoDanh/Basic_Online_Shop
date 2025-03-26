package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_product", nullable = false)
    private int idProduct;
    @Basic
    @Column(name = "product_name", nullable = false, length = 225)
    private String productName;
    @Basic
    @Column(name = "price", nullable = false)
    private int price;
    @Basic
    @Column(name = "img_url", nullable = false, length = 225)
    private String imgUrl;
    @Basic
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;
    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Basic
    @Column(name = "description", length = -1)
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
