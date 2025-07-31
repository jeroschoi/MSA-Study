package com.event.memberservice.controller;

import com.event.memberservice.dto.MemberCommonResponse;
import com.event.memberservice.dto.MemberJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Member Controller REST API 테스트")
class MemberControllerRestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("존재하지 않는 회원 조회 - 404 에러")
    void testGetMember_NotFound() {
        // When
        ResponseEntity<MemberCommonResponse> response =
                restTemplate.getForEntity("/msa/v1/members/nonexistentuser", MemberCommonResponse.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().isSuccess()).isFalse();
        assertThat(response.getBody().getErrorCode()).isEqualTo("M007");
    }

    // Space 규칙: 30라인 이하 헬퍼 메서드
    private MemberJoinRequest createValidMemberRequest() {
        return MemberJoinRequest.builder()
                .userId("testuser")
                .name("테스트사용자")
                .email("test@example.com")
                .contact("010-1234-5678")
                .password("password123")
                .build();
    }
}
