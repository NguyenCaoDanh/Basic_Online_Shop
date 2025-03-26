package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "banner")
public class Banner {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_banner", nullable = false)
    private int idBanner;
    @Basic
    @Column(name = "img_url", nullable = false, length = 225)
    private String imgUrl;
    @Basic
    @Column(name = "status", nullable = false, length = -1)
    private String status;


}
