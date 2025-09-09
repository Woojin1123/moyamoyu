package com.moyamoyu.repository;

import com.moyamoyu.entity.MoimMember;
import com.moyamoyu.entity.enums.MoimRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoimMemberRepository extends JpaRepository<MoimMember,Long> {
    Optional<MoimMember> findByMemberId(Long memberId);

    long countByMemberId(Long leaderId);

    boolean existsByMemberIdAndMoimId(Long memberId, Long moimId);

    MoimMember findByMoimIdAndRole(Long moimId, MoimRole role);
}
