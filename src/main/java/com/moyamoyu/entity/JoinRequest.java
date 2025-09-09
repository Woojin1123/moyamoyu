package com.moyamoyu.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime decidedAt;
    private String message;
    private String rejectReason;
    @Enumerated(EnumType.STRING)
    private JoinRequestStatus status = JoinRequestStatus.PENDING;

    @Builder
    public JoinRequest(LocalDateTime createdAt, String message, User participant, User leader, Moim moim) {
        this.createdAt = createdAt;
        this.message = message;
        this.participant = participant;
        this.leader = leader;
        this.moim = moim;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private User participant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private User leader;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moim_id")
    private Moim moim;

    public void approve() {
        this.status = JoinRequestStatus.APPROVED;
    }
}
