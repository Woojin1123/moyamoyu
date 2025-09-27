package com.moyamoyu.service;

import com.moyamoyu.dto.AuthUser;
import com.moyamoyu.dto.request.UserDeleteRequest;
import com.moyamoyu.dto.request.UserInfoPatchRequest;
import com.moyamoyu.dto.response.MyUserInfoResponse;
import com.moyamoyu.dto.response.UserSimpleResponse;
import com.moyamoyu.entity.User;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import com.moyamoyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MyUserInfoResponse getMyInfo(AuthUser authUser) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        String fullAddress = user.getRoadAddress() +" "+ user.getDetailAddress();

        return MyUserInfoResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .fullAddress(fullAddress)
                .introduce(user.getIntroduce())
                .profileImg(user.getProfileImgUrl())
                .build();
    }

    @Transactional(readOnly = true)
    public UserSimpleResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );

        return UserSimpleResponse.builder()
                .introduce(user.getIntroduce())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public MyUserInfoResponse patchUserInfo(AuthUser authUser, UserInfoPatchRequest userInfoPatchRequest) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );

        user.patchInfo(userInfoPatchRequest);
        User savedUser = userRepository.save(user);

        return MyUserInfoResponse.builder()
                .nickname(user.getNickname())
                .fullAddress(user.getRoadAddress() + user.getDetailAddress())
                .introduce(user.getIntroduce())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Transactional
    public Long deleteUser(AuthUser authUser, UserDeleteRequest userDeleteRequest) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );
        if(!passwordEncoder.matches(userDeleteRequest.password(),user.getPassword())){
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        };
        user.delete();
        userRepository.save(user);
        return user.getId();
    }
}
