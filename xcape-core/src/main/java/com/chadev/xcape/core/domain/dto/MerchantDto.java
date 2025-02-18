package com.chadev.xcape.core.domain.dto;

import com.chadev.xcape.core.domain.entity.Merchant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDto {
    private Long id;
    //    private Long accountId;
    private String name;
    private String address;
    private String telNumber;
    private String businessHour;
    private Boolean parkingYn;
    private String ceoName;
    private String businessRegistrationNumber;
    private String email;

    private List<ThemeDto> themeList = new ArrayList<>();

    public MerchantDto(Merchant merchant) {
        this.id = merchant.getId();
//        this.accountId = accountId;
        this.name = merchant.getName();
        this.address = merchant.getAddress();
        this.telNumber = merchant.getTelNumber();
        this.businessHour = merchant.getBusinessHour();
        this.parkingYn = merchant.getParkingYn();
        this.ceoName = merchant.getCeoName();
        this.businessRegistrationNumber = merchant.getBusinessRegistrationNumber();
        this.email = merchant.getEmail();
        this.themeList = merchant.getThemeList().stream().map(ThemeDto::new).collect(Collectors.toList());
    }
}