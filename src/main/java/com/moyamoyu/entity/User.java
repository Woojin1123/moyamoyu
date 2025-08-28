package com.moyamoyu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private MoimRole moimRole;
    private ServiceRole serviceRole;
    @OneToMany(mappedBy = "member")
    private List<MoimMember> moimMembers = new ArrayList<>();
}
