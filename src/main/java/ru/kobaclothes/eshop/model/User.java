package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "active_status")
    boolean activeStatus;
    
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AccountInfo accountInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DeliveryInfo> deliveryInfo;

    @Column(name = "verified")
    private boolean verified;

}
