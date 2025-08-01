package com.event.memberservice.member.controller;

import com.event.memberservice.common.response.ApiResponse;
import com.event.memberservice.member.dto.MemberJoinRequest;
import com.event.memberservice.member.dto.MemberResponse;
import com.event.memberservice.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/msa/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member Management", description = "회원 관리 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 성공 시 201 Created 반환
    @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
    public ApiResponse<MemberResponse> join(@Valid @RequestBody MemberJoinRequest request) {

        log.info("회원가입 요청: userId={}", request.getUserId());

        MemberResponse response = memberService.join(request);
        return ApiResponse.success(response, "회원가입이 완료되었습니다.");
    }

    @GetMapping("/{userId}")
    @Operation(summary = "회원 조회 (ID)", description = "사용자 ID로 회원 정보를 조회합니다.")
    public ApiResponse<MemberResponse> findByUserId(
            @Parameter(description = "사용자 ID", example = "testuser")
            @PathVariable String userId) {

        log.info("회원 조회 요청 (ID): userId={}", userId);

        MemberResponse member = memberService.findByUserId(userId);
        return ApiResponse.success(member);
    }

    @GetMapping("/contact/{contact}")
    @Operation(summary = "회원 조회 (연락처)", description = "연락처로 회원 정보를 조회합니다.")
    public ApiResponse<MemberResponse> findByContact(
            @Parameter(description = "연락처", example = "010-1234-5678")
            @PathVariable String contact) {

        log.info("회원 조회 요청 (연락처): contact={}", contact);

        MemberResponse member = memberService.findByContact(contact);
        return ApiResponse.success(member);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "회원탈퇴", description = "회원을 탈퇴 처리합니다.")
    public ApiResponse<Void> exit(
            @Parameter(description = "사용자 ID", example = "testuser")
            @PathVariable String userId) {

        log.info("회원탈퇴 요청: userId={}", userId);

        memberService.exit(userId);
        return ApiResponse.success(null, "회원탈퇴가 정상적으로 처리되었습니다.");
    }

    @GetMapping
    @Operation(summary = "활성 회원 목록 조회", description = "활성 상태인 모든 회원 목록을 조회합니다.")
    public ApiResponse<List<MemberResponse>> getAllActiveMembers() {

        log.info("활성 회원 목록 조회 요청");

        List<MemberResponse> members = memberService.getAllActiveMembers();
        return ApiResponse.success(members);
    }

}
