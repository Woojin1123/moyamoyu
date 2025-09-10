package com.moyamoyu.repository;

import com.moyamoyu.entity.Moim;
import com.moyamoyu.entity.MoimMember;
import com.moyamoyu.entity.enums.MoimRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoimMemberRepository extends JpaRepository<MoimMember,Long> {
    Optional<MoimMember> findByMemberId(Long memberId);

    long countByMemberId(Long leaderId);

    boolean existsByMemberIdAndMoimId(Long memberId, Long moimId);

    MoimMember findByMoimIdAndRole(Long moimId, MoimRole role);

    Long findMemberIdByMoimIdAndRole(Long moimId, MoimRole leader);

    @Query("select mm.moim from MoimMember mm where mm.member.id = :userId")
    Page<Moim> findMoimsByMemberId(Long userId, Pageable pageable);
}
