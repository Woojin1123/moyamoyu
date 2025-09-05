package com.moyamoyu.entity;

import com.moyamoyu.entity.enums.JoinPolicy;
import com.moyamoyu.entity.enums.MoimCategory;
import com.moyamoyu.entity.enums.MoimGrade;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
public class Moim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private JoinPolicy joinPolicy;
    @Enumerated(EnumType.STRING)
    private MoimCategory category;
    @Enumerated(EnumType.STRING)
    private MoimGrade grade;
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;
    private Long memberCount;

    @Builder
    public Moim(String name, String description, String joinPolicy, String category) {
        this.name = name;
        this.description = description;
        this.joinPolicy = JoinPolicy.valueOf(joinPolicy);
        this.category = MoimCategory.valueOf(category);
        this.grade = MoimGrade.BASIC;
        this.memberCount = 0L;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void update(String name, String description, String joinPolicy) {
        if(name != null) this.name = name;
        if(description != null) this.description = description;
        if(joinPolicy != null) this.joinPolicy = JoinPolicy.valueOf(joinPolicy);
    }
}
