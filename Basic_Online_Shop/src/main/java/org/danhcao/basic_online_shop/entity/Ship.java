package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ship")
public class Ship {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_ship", nullable = false)
    private int idShip;
    @Basic
    @Column(name = "shipping_address", nullable = false, length = 225)
    private String shippingAddress;
    @Basic
    @Column(name = "shipping_status", nullable = false, length = 45)
    private String shippingStatus;
    @Basic
    @Column(name = "tracking_number", length = 45)
    private String trackingNumber;
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;


}
