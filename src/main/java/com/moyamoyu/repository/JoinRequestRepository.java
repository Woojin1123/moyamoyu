package com.moyamoyu.repository;

import com.moyamoyu.entity.JoinRequest;
import com.moyamoyu.entity.JoinRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    boolean existsByParticipantIdAndMoimId(Long id, Long moimId);

    Optional<JoinRequest> findByIdAndStatus(Long requestId, JoinRequestStatus status);
}
