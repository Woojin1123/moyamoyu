package com.moyamoyu.service;

import com.moyamoyu.dto.AuthUser;
import com.moyamoyu.dto.request.MoimCreateRequest;
import com.moyamoyu.dto.request.MoimUpdateRequest;
import com.moyamoyu.dto.response.SimpleMoimResponse;
import com.moyamoyu.entity.Moim;
import com.moyamoyu.entity.MoimMember;
import com.moyamoyu.entity.User;
import com.moyamoyu.entity.enums.MoimCategory;
import com.moyamoyu.entity.enums.MoimRole;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import com.moyamoyu.repository.MoimMemberRepository;
import com.moyamoyu.repository.MoimRepository;
import com.moyamoyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MoimService {
    private final MoimRepository moimRepository;
    private final MoimMemberRepository moimMemberRepository;
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
                .member(user)
                .moim(savedMoim)
                .role(MoimRole.LEADER)
                .build();
        moimMemberRepository.save(moimMember);

        return SimpleMoimResponse.from(savedMoim);
    }

    public Page<SimpleMoimResponse> findMoims(int page, String category) {
        Pageable pageable = PageRequest.of(page, 10);
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
}
