package com.event.memberservice.member.controller;

import com.event.memberservice.member.dto.MemberJoinRequest;
import com.event.memberservice.member.dto.MemberResponse;
import com.event.memberservice.member.exception.MemberErrorCode;
import com.event.memberservice.member.exception.MemberException;
import com.event.memberservice.member.repository.entity.MessageType;
import com.event.memberservice.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("MemberController 테스트")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        Mockito.reset(memberService);
    }

    @Test
    @DisplayName("회원가입 성공")
    void join_Success() throws Exception {
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("testuser123")
                .password("Password123!")
                .name("테스트사용자")
                .email("testuser@example.com")
                .contact("010-1234-5678")
                .messageType(MessageType.SMS)
                .build();

        MemberResponse response = MemberResponse.builder()
                .id(1L)
                .userId("testuser123")
                .name("테스트사용자")
                .email("testuser@example.com")
                .contact("010-1234-5678")
                .messageType(MessageType.SMS)
                .active(true)
                .build();

        when(memberService.join(any(MemberJoinRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/msa/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value("testuser123"))
                .andDo(print());

        verify(memberService, times(1)).join(any(MemberJoinRequest.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 입력값 검증 실패")
    void join_Fail_InvalidInput() throws Exception {
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("ab")
                .password("123")
                .name("")
                .contact("123-456-789")
                .email("invalid-email")
                .messageType(null)
                .build();

        mockMvc.perform(post("/msa/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(print());

        verify(memberService, never()).join(any(MemberJoinRequest.class));
    }

    @Test
    @DisplayName("회원 조회 실패 - 존재하지 않는 회원")
    void findByUserId_Fail_MemberNotFound() throws Exception {
        String nonexistentUserId = "nonexistent123";

        when(memberService.findByUserId(nonexistentUserId))
                .thenThrow(new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        mockMvc.perform(get("/msa/v1/members/{userId}", nonexistentUserId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(MemberErrorCode.MEMBER_NOT_FOUND.getCode()))
                .andDo(print());

        verify(memberService, times(1)).findByUserId(nonexistentUserId);
    }
}
