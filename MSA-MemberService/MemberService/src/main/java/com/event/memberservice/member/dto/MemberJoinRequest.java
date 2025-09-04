package com.event.memberservice.member.dto;

import com.event.memberservice.member.repository.entity.MessageType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequest {

    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "사용자 ID는 4-20자의 영문자와 숫자만 가능합니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
            message = "비밀번호는 8자 이상이며, 영문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 20, message = "이름은 2-20자 사이여야 합니다.")
    private String name;

    @NotBlank(message = "연락처는 필수입니다.")
    @Pattern(regexp = "^01[0-9]-[0-9]{4}-[0-9]{4}$", message = "올바른 연락처 형식이 아닙니다.")
    private String contact;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotNull(message = "메시지 타입은 필수입니다.")
    private MessageType messageType;
}