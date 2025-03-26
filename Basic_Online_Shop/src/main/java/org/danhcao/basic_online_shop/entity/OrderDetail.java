package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "order_detail", schema = "base_shop")
public class OrderDetail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_order_detail", nullable = false)
    private int idOrderDetail;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Basic
    @Column(name = "price", nullable = false)
    private int price;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
