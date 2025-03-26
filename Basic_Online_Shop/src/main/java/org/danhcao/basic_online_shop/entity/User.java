package org.danhcao.basic_online_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;

    @Basic
    @Column(name = "full_name", nullable = false, length = 225)
    private String fullName;


    @Basic
    @Column(name = "email", nullable = false, length = 225)
    private String email;

    @Basic
    @Column(name = "phone", nullable = false, length = 45)
    private String phone;

    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    @Basic
    @Column(name = "dob", nullable = false)
    private Date dob;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
