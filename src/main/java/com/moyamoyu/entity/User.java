package com.moyamoyu.entity;

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
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private ServiceRole serviceRole;

    @Builder
    public User(String email, String password, String nickname, LocalDateTime createdAt, ServiceRole serviceRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.serviceRole = serviceRole;
    }
}
