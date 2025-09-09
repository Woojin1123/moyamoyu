package com.moyamoyu.entity;

import com.moyamoyu.entity.enums.MoimMemberStatus;
import com.moyamoyu.entity.enums.MoimRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MoimMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MoimMemberStatus status;
    private String reason;
    @Enumerated(EnumType.STRING)
    private MoimRole role;
    private LocalDateTime joinedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moim_id")
    private Moim moim;

    @Builder
    public MoimMember(User joinedUser, Moim moim, MoimRole moimRole) {
        this.member = joinedUser;
        this.moim = moim;
        this.role = moimRole;
        this.joinedAt = LocalDateTime.now();
        this.status = MoimMemberStatus.ACTIVE;
    }
}
