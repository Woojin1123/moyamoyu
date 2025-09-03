package com.moyamoyu.service;

import com.moyamoyu.dto.request.LoginRequest;
import com.moyamoyu.dto.request.SignUpRequest;
import com.moyamoyu.dto.response.LoginResponse;
import com.moyamoyu.entity.ServiceRole;
import com.moyamoyu.entity.User;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import com.moyamoyu.repository.UserRepository;
import com.moyamoyu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() ->
                new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        String accessToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getServiceRole().name(), false);

        return new LoginResponse(null, accessToken);
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.email())){
            throw new ApiException(ErrorCode.RESOURCE_ALREADY_EXISTS);
        };

        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        User user = User.builder()
                .email(signUpRequest.email())
                .nickname(signUpRequest.nickname())
                .password(encodedPassword)
                .serviceRole(ServiceRole.ROLE_USER)
                .build();

        User savedUser = userRepository.save(user);
    }
}
