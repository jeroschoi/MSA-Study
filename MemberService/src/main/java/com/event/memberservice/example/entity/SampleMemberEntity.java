package com.event.memberservice.example.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@ToString
@Getter
@Table(name = "sample_member")
@Entity
@Builder
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class SampleMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
}
