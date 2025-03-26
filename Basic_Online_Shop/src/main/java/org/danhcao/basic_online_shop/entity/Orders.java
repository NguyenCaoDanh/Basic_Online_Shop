package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "orders")
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_orders", nullable = false)
    private int idOrder;
    @Basic
    @Column(name = "total_price", nullable = false)
    private int totalPrice;
    @Basic
    @Column(name = "status", nullable = false, length = 45)
    private String status;
    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Ship ship;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

}
