package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Setter
@Getter
@Entity
@Table(name = "notification")
public class Notification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_notification", nullable = false)
    private int idNotification;
    @Basic
    @Column(name = "notification", nullable = false, length = 225)
    private String notification;
    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Basic
    @Column(name = "status", nullable = false)
    private byte status;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


}
