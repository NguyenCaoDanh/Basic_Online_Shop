package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "review")
public class Review {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_review", nullable = false)
    private int idReview;
    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;
    @Basic
    @Column(name = "comment", length = -1)
    private String comment;
    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
