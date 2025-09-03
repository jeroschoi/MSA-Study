package com.event.authservice.service;

import com.event.authservice.dto.*;
import com.event.authservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        // 1. ID/PASS 검증 (Member 테이블? Login 테이블? ) - 인증 처리
        // 2. Refresh Token 생성 후 저장 (DB or Redis)
        // 3. JWT 토큰 생성 - 무슨 정보를 담을지
        // 4. Token Redis 저장
        // 5. TokenResponseDto 반환
        return TokenResponseDto.builder().build();
    }

    @Transactional
    public TokenResponseDto reissue(TokenReissueRequestDto reissueRequestDto) {
        // 1. Refresh Token 검증
        // 위의 3~5번 동일
        return TokenResponseDto.builder().build();
    }

    @Transactional
    public TokenCheckResponseDto checkToken(TokenCheckRequestDto tokenCheckRequestDto) {
        // Redis 에서 해당 토큰이 존재 하는지 여부 확인 - 인증 확인
        return TokenCheckResponseDto.builder().build();
    }
}
