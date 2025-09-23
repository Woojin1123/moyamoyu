package com.moyamoyu.service;

import com.moyamoyu.dto.request.LoginRequest;
import com.moyamoyu.dto.request.SignUpRequest;
import com.moyamoyu.dto.response.TokenResponse;
import com.moyamoyu.entity.User;
import com.moyamoyu.entity.enums.ServiceRole;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import com.moyamoyu.repository.RedisTokenService;
import com.moyamoyu.repository.UserRepository;
import com.moyamoyu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTokenService redisTokenService;
    private final JavaMailSender javaMailSender;
    private final JwtUtil jwtUtil;

    public TokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() ->
                new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"비밀번호가 틀렸습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getServiceRole().name());
        String refreshToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail());

        redisTokenService.saveRefreshToken(user.getEmail(), refreshToken);

        return new TokenResponse(refreshToken, accessToken);
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new ApiException(ErrorCode.RESOURCE_ALREADY_EXISTS);
        }

        if(!redisTokenService.isVerified(signUpRequest.email())){
            throw new ApiException(ErrorCode.BAD_REQUEST, "이메일 인증이 필요합니다.");
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        User user = User.builder()
                .email(signUpRequest.email())
                .nickname(signUpRequest.nickname())
                .password(encodedPassword)
                .serviceRole(ServiceRole.ROLE_USER)
                .roadAddress(signUpRequest.roadAddress())
                .detailAddress(signUpRequest.detailAddress())
                .zipcode(signUpRequest.zipcode())
                .build();

        User savedUser = userRepository.save(user);
    }

    public void logout(String refreshToken) {
        String email = jwtUtil.getEmail(refreshToken);
        redisTokenService.deleteRefreshToken(email);
    }

    public TokenResponse refresh(String refreshToken) {
        String email = jwtUtil.getEmail(refreshToken);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND)
        );

        String newAccessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getServiceRole().name());
        String newRefreshToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail());
        redisTokenService.saveRefreshToken(user.getEmail(), newRefreshToken);

        return new TokenResponse(newRefreshToken, newAccessToken);
    }

    public void confirmEmail(String email, String token) {
        redisTokenService.verifyEmailToken(email, token);
    }

    public void verifyEmail(String email) {
        String token = redisTokenService.saveEmailToken(email);

        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setTo(email);
            message.setSubject("MoyaMoyu 이메일 인증 코드");
            message.setText("인증 토큰 : " + token);
            javaMailSender.send(message);

            log.info("메일 발송 성공");
        } catch (Exception e) {
            log.info("메일 발송 실패");
            throw new ApiException(ErrorCode.API_REQUEST_FAILED);
        }
    }
}
