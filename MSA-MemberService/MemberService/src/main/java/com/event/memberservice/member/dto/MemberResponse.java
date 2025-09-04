package com.event.memberservice.member.dto;

import com.event.memberservice.member.repository.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

    private Long id;
    private String userId;
    private String contact;
    private MessageType messageType;
    private String name;
    private String email;
    private String address;
    private LocalDateTime joinDate;
    private LocalDateTime exitDate;
    private boolean active;
    private LocalDateTime updatedAt;
}