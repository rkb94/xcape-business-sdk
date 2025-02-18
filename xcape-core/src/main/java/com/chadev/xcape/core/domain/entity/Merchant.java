package com.chadev.xcape.core.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Setter
    @Column(name = "merchant_name")
    private String name;

    @Setter
    @Column(name = "address")
    private String address;

    @Setter
    @Column(name = "tel_number")
    private String telNumber;

    @Setter
    @Column(name = "business_hour")
    private String businessHour;

    @Setter
    @Column(name = "parking_yn")
    private Boolean parkingYn;

    @Setter
    @Column(name = "ceo_name")
    private String ceoName;

    @Setter
    @Column(name = "business_registration_number")
    private String businessRegistrationNumber;

    @Setter
    @Column(name = "email")
    private String email;

    @Setter
    @OneToMany(mappedBy = "merchant")
    private List<Theme> themeList = new ArrayList<>();

}
