package com.event.messageservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import com.event.messageservice.dto.MessageType;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_MESSAGE_HISTORY")
public class MessageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원아이디")
    private String memberId;

    @Comment("회원 연락처")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Comment("메시지 타입")
    private MessageType messageType;

    @Comment("메시지 내용")
    private String content;

    @Comment("메시지 전송 시간")
    @Builder.Default // 기본값으로 현재 시간을 설정
    private LocalDateTime sentAt = LocalDateTime.now();

    @Comment("메시지 노출 여부")
    @Builder.Default // 기본값으로 true 설정
    private boolean visible = true;

    @Override
    public String toString() {
        return "MessageHistory{" +
                "id=" + id +
                ", memberId='" + memberId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", messageType=" + messageType +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                ", visible=" + visible +
                '}';
    }
}
