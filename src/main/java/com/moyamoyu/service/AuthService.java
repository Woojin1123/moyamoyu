package com.moyamoyu.service;

import com.moyamoyu.controller.LoginRequest;
import com.moyamoyu.dto.response.LoginResponse;
import com.moyamoyu.entity.User;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import com.moyamoyu.repository.UserRepository;
import com.moyamoyu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        return new LoginResponse(null,accessToken);
    }
}
