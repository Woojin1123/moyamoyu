package com.moyamoyu.entity;

import jakarta.persistence.*;
import lombok.Getter;

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
}
