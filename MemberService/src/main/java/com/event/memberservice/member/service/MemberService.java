package com.event.memberservice.member.service;

import com.event.memberservice.member.dto.MemberJoinRequest;
import com.event.memberservice.member.dto.MemberResponse;
import com.event.memberservice.member.exception.MemberErrorCode;
import com.event.memberservice.member.exception.MemberException;
import com.event.memberservice.member.mapper.MemberMapper;
import com.event.memberservice.member.repository.MemberRepository;
import com.event.memberservice.member.repository.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public MemberResponse join(MemberJoinRequest request) {
        try {
            validateDuplicateMember(request);
            MemberEntity memberEntity = createMemberEntity(request);
            MemberEntity savedMemberEntity = saveMember(memberEntity);
            sendJoinMessage(savedMemberEntity);
            return memberMapper.toResponse(savedMemberEntity);
            
        } catch (DataIntegrityViolationException e) {
            log.error("회원가입 DB 오류: {} - {}", request.getUserId(), e.getMessage());
            throw new MemberException(MemberErrorCode.DATABASE_ERROR);
        } catch (MemberException e) {
            throw e;
        } catch (Exception e) {
            log.error("회원가입 중 예상치 못한 오류: {} - {}", request.getUserId(), e.getMessage());
            throw new MemberException(MemberErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private MemberEntity createMemberEntity(MemberJoinRequest request) {
        MemberEntity memberEntity = memberMapper.toEntity(request);
        memberEntity.setPassword(request.getPassword());
        return memberEntity;
    }

    private MemberEntity saveMember(MemberEntity memberEntity) {
        return memberRepository.save(memberEntity);
    }

    private void sendJoinMessage(MemberEntity memberEntity) {
        try {
            // 메시지 전송 로직
            // webclient 전송 or messageSendService.sendMessage
        } catch (Exception e) {
            log.error("회원가입 메시지 전송 실패: {} - {}", memberEntity.getUserId(), e.getMessage());
        }
    }

    private void validateDuplicateMember(MemberJoinRequest request) {
        // userId
        if (memberRepository.findByUserId(request.getUserId()).isPresent()) {
            throw new MemberException(MemberErrorCode.DUPLICATE_USER_ID);
        }
        
        // email
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }
        
        // contact
        if (memberRepository.findByContact(request.getContact()).isPresent()) {
            throw new MemberException(MemberErrorCode.DUPLICATE_CONTACT);
        }
    }

    @Transactional
    public void exit(String userId) {
        try {
            MemberEntity memberEntity = findAndValidateMember(userId);
            memberEntity.setActive(false);
            memberEntity.setExitDate(LocalDateTime.now());
            // TODO - 탈퇴 메시지 전송로직 (Kafka event 발행 처리)

        } catch (Exception e) {
            log.error("회원탈퇴 중 예상치 못한 오류: {} - {}", userId, e.getMessage());
            throw new MemberException(MemberErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private MemberEntity findAndValidateMember(String userId) {
        MemberEntity memberEntity = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!memberEntity.isActive()) {
            throw new MemberException(MemberErrorCode.ALREADY_EXITED);
        }
        
        return memberEntity;
    }

    @Transactional(readOnly = true)
    public MemberResponse findByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .map(memberMapper::toResponse)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public MemberResponse findByContact(String contact) {
        return memberRepository.findByContact(contact)
                .map(memberMapper::toResponse)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Optional<MemberResponse> findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(memberMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getAllActiveMembers() {
        return memberMapper.toActiveResponseList(memberRepository.findByActiveTrue());
    }
}
