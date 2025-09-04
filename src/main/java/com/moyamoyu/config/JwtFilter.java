package com.moyamoyu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.AuthUser;
import com.moyamoyu.entity.ServiceRole;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jwt = jwtUtil.substringToken(token);
            try {
                Claims claims = jwtUtil.extractClaims(jwt);
                Long userId = Long.valueOf(claims.getSubject());
                String email = String.valueOf(claims.get("email"));
                ServiceRole role = ServiceRole.of(String.valueOf(claims.get("role")));

                AuthUser authUser = AuthUser.builder()
                        .id(userId)
                        .email(email)
                        .build();
                if(SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    authUser, null,
                                    Collections.singletonList(new SimpleGrantedAuthority(role.name()))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ApiException e) {
                if (!request.getRequestURI().startsWith("/api/auth/refresh")) {
                    log.error("JWT 검증 실패", e);
                    response.setStatus(e.getHttpStatus().value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");

                    ApiResponse<Object> errorResponse = ApiResponse.failure(e);
                    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                    return;
                }
            }

        }
        filterChain.doFilter(request, response);
    }
}
