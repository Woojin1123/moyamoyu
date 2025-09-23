package com.moyamoyu.entity;

import com.moyamoyu.dto.request.UserInfoPatchRequest;
import com.moyamoyu.entity.enums.ServiceRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isDeleted = false;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private ServiceRole serviceRole;
    private String roadAddress;
    private String detailAddress;
    private String zipcode;
    private String introduce;

    @Builder
    public User(String email, String password, String nickname, LocalDateTime createdAt, ServiceRole serviceRole, String roadAddress, String detailAddress, String zipcode) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.serviceRole = serviceRole;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
        this.createdAt = LocalDateTime.now();
    }

    public void patchInfo(UserInfoPatchRequest request) {
        if(!(request.nickname() == null)) this.nickname = request.nickname();
        if(!(request.introduce() == null)) this.introduce = request.introduce();
        if(!(request.detailAddress() == null)) this.detailAddress = request.detailAddress();
        if(!(request.roadAddress() == null)) this.roadAddress = request.roadAddress();
        if(!(request.zipcode() == null)) this.zipcode = request.zipcode();
    }

    public void delete() {
        this.isDeleted = true;
    }
}
