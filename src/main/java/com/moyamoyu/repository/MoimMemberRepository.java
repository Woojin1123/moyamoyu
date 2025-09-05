package com.moyamoyu.repository;

import com.moyamoyu.entity.MoimMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoimMemberRepository extends JpaRepository<MoimMember,Long> {
    Optional<MoimMember> findByMemberId(Long leaderId);

    long countByMemberId(Long leaderId);
}
