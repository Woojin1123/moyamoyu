package com.moyamoyu.repository;

import com.moyamoyu.entity.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    boolean existsByParticipantIdAndMoimId(Long id, Long moimId);
}
