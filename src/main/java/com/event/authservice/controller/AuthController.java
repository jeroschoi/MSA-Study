package com.event.authservice.controller;

import com.event.authservice.dto.*;
import com.event.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msa/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "인증 관리 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 ID와 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    public ResponseEntity<ServiceCommonResponseDto<TokenResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(ServiceCommonResponseDto.success(authService.login(loginRequestDto)    ));
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "Refresh Token을 사용하여 새로운 Access Token을 발급받습니다.")
    public ResponseEntity<ServiceCommonResponseDto<TokenResponseDto>> reissue(@RequestBody TokenReissueRequestDto reissueRequestDto) {
        return ResponseEntity.ok(ServiceCommonResponseDto.success(authService.reissue(reissueRequestDto)));
    }

    @PostMapping("/check/token")
    @Operation(summary = "토큰 확인", description = "토큰 정보가 유효한지 확인합니다.")
    public ResponseEntity<ServiceCommonResponseDto<TokenCheckResponseDto>> checkToken(@RequestBody TokenCheckRequestDto tokenCheckRequestDto) {
        return ResponseEntity.ok(ServiceCommonResponseDto.success(authService.checkToken(tokenCheckRequestDto)));
    }
}