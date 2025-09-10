package com.moyamoyu.service;

import com.moyamoyu.dto.AuthUser;
import com.moyamoyu.dto.request.JoinReasonRequest;
import com.moyamoyu.dto.request.MoimCreateRequest;
import com.moyamoyu.dto.request.MoimUpdateRequest;
import com.moyamoyu.dto.response.JoinMoimResponse;
import com.moyamoyu.dto.response.SimpleJoinRequest;
import com.moyamoyu.dto.response.SimpleMoimResponse;
import com.moyamoyu.entity.*;
import com.moyamoyu.entity.enums.MoimCategory;
import com.moyamoyu.entity.enums.MoimRole;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import com.moyamoyu.repository.JoinRequestRepository;
import com.moyamoyu.repository.MoimMemberRepository;
import com.moyamoyu.repository.MoimRepository;
import com.moyamoyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MoimService {
    private final MoimRepository moimRepository;
    private final MoimMemberRepository moimMemberRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public SimpleMoimResponse createMoim(MoimCreateRequest moimCreateRequest) {
        User user = userRepository.findById(moimCreateRequest.leaderId()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        long ownedMoimCount = moimMemberRepository.countByMemberId(user.getId());

        if (ownedMoimCount >= 3) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "한 유저는 최대 3개의 모임만 생성할 수 있습니다.");
        }

        Moim moim = Moim.builder()
                .name(moimCreateRequest.name())
                .description(moimCreateRequest.description())
                .category(moimCreateRequest.category())
                .joinPolicy(moimCreateRequest.joinPolicy())
                .build();
        Moim savedMoim = moimRepository.save(moim);

        MoimMember moimMember = MoimMember.builder()
                .joinedUser(user)
                .moim(savedMoim)
                .moimRole(MoimRole.LEADER)
                .build();
        moimMemberRepository.save(moimMember);

        return SimpleMoimResponse.from(savedMoim);
    }

    @Transactional(readOnly = true)
    public Page<SimpleMoimResponse> findMoims(int page, String category) {
        Pageable pageable = PageRequest.of(page, 50);
        Page<Moim> moims = moimRepository.findAllByCategory(MoimCategory.valueOf(category), pageable);
        return moims.map(SimpleMoimResponse::from);
    }

    @Transactional
    public Long deleteMoim(AuthUser authUser, Long moimId) {
        MoimMember moimMember = moimMemberRepository.findByMemberId(authUser.getId()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        if (!moimMember.getRole().name().equals("LEADER")) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }
        Moim moim = moimMember.getMoim();
        moim.delete();
        moimRepository.save(moim);
        return moim.getId();
    }

    @Transactional
    public SimpleMoimResponse updateMoim(AuthUser authUser, Long moimId, MoimUpdateRequest moimUpdateRequest) {
        MoimMember moimMember = moimMemberRepository.findByMemberId(authUser.getId()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        if (!moimMember.getRole().name().equals("LEADER")) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }
        Moim moim = moimMember.getMoim();
        moim.update(moimUpdateRequest.name(), moimUpdateRequest.description(), moimUpdateRequest.joinPolicy());
        Moim savedMoim = moimRepository.save(moim);
        return SimpleMoimResponse.from(savedMoim);
    }

    @Transactional
    public JoinMoimResponse joinMoim(AuthUser authUser, Long moimId, JoinReasonRequest joinReasonRequest) {
        Moim moim = moimRepository.findById(moimId).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );

        if (moimMemberRepository.existsByMemberIdAndMoimId(authUser.getId(), moimId) ||
                joinRequestRepository.existsByParticipantIdAndMoimId(authUser.getId(), moimId)) {
            throw new ApiException(ErrorCode.RESOURCE_ALREADY_EXISTS);
        }

        MoimMember leaderMoimMember = moimMemberRepository.findByMoimIdAndRole(moim.getId(), MoimRole.LEADER);

        User participant = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );

        User leader = leaderMoimMember.getMember();

        JoinRequest joinRequest = JoinRequest.builder()
                .createdAt(LocalDateTime.now())
                .participant(participant)
                .leader(leader)
                .message(joinReasonRequest.message())
                .build();

        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);
        return new JoinMoimResponse(moim.getId(), savedJoinRequest.getStatus().name());
    }

    @Transactional
    public Long approveJoinMoim(AuthUser authUser, Long moimId, Long requestId) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        Long leaderId = moimMemberRepository.findMemberIdByMoimIdAndRole(moimId, MoimRole.LEADER);
        if (!user.getId().equals(leaderId)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED, "모임의 리더가 아닙니다");
        }

        JoinRequest joinRequest = joinRequestRepository.findByIdAndStatus(requestId, JoinRequestStatus.PENDING).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "이미 처리 됬거나 없는 요청입니다.")
        );
        joinRequest.approve();
        joinRequestRepository.save(joinRequest);

        User participant = joinRequest.getParticipant();
        Moim moim = joinRequest.getMoim();

        MoimMember moimMember = MoimMember.builder()
                .joinedUser(participant)
                .moim(moim)
                .moimRole(MoimRole.MEMBER)
                .build();

        moimMemberRepository.save(moimMember);

        return requestId;
    }

    @Transactional
    public Long rejectJoinMoim(AuthUser authUser, Long moimId, Long requestId, String reason) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        Long leaderId = moimMemberRepository.findMemberIdByMoimIdAndRole(moimId, MoimRole.LEADER);
        if (!user.getId().equals(leaderId)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED, "모임의 리더가 아닙니다");
        }

        JoinRequest joinRequest = joinRequestRepository.findByIdAndStatus(requestId, JoinRequestStatus.PENDING).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "이미 처리 됬거나 없는 요청입니다.")
        );
        joinRequest.reject(reason);
        joinRequestRepository.save(joinRequest);

        return requestId;
    }

    @Transactional(readOnly = true)
    public Page<SimpleJoinRequest> findAllJoinRequest(AuthUser authUser, Long moimId, String status, int page, int size) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        Long leaderId = moimMemberRepository.findMemberIdByMoimIdAndRole(moimId, MoimRole.LEADER);
        if (!user.getId().equals(leaderId)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED, "모임의 리더가 아닙니다");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<JoinRequest> joinRequests = joinRequestRepository.findAllByStatus(JoinRequestStatus.valueOf(status), pageable);


        return joinRequests.map(
                joinRequest -> SimpleJoinRequest.builder()
                        .id(joinRequest.getId())
                        .createdAt(joinRequest.getCreatedAt())
                        .message(joinRequest.getMessage())
                        .joinRequestStatus(joinRequest.getStatus())
                        .build());
    }

    @Transactional(readOnly = true)
    public Page<SimpleMoimResponse> findJoinedMoims(int page, AuthUser authUser) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        Pageable pageable = PageRequest.of(page,50);
        Page<Moim> moims = moimMemberRepository.findMoimsByMemberId(user.getId(),pageable);
        return moims.map(SimpleMoimResponse::from);
    }
}
