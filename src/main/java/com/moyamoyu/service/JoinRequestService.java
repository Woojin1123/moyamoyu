package com.moyamoyu.service;

import com.moyamoyu.dto.response.SimpleJoinRequestResponse;
import com.moyamoyu.entity.JoinRequest;
import com.moyamoyu.entity.JoinRequestStatus;
import com.moyamoyu.repository.JoinRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;

    public Page<SimpleJoinRequestResponse> findAllReceivedRequest(int page, int size, Long leaderId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JoinRequest> joinRequests;

        joinRequests = joinRequestRepository.findAllByLeaderIdAndStatus(leaderId, JoinRequestStatus.PENDING, pageable);


        return joinRequests.map((e) -> SimpleJoinRequestResponse.builder()
                .id(e.getId())
                .moimId(e.getMoim().getId())
                .moimName(e.getMoim().getName())
                .participantId(e.getParticipant().getId())
                .requestedAt(e.getCreatedAt())
                .joinRequestStatus(e.getStatus())
                .message(e.getMessage())
                .nickname(e.getParticipant().getNickname())
                .build());
    }
}
