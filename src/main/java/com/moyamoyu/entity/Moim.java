package com.moyamoyu.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Moim {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private JoinPolicy joinPolicy;
    private Long capacity;
    private Long memberCount;
    @OneToMany(mappedBy = "moim")
    private List<MoimMember> moimMembers = new ArrayList<>();

    @OneToMany(mappedBy = "moim")
    private List<JoinRequest> joinRequests = new ArrayList<>();
}
