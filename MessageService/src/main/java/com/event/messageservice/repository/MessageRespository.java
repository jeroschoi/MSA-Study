package com.event.messageservice.repository;


import com.event.messageservice.entity.MessageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRespository extends JpaRepository<MessageHistory, Long> {


    List<MessageHistory> findByMemberIdAndVisibleTrueOrderBySentAtDesc(String memberId);

    List<MessageHistory> findByPhoneNumberAndVisibleTrueOrderBySentAtDesc(String phoneNumber);
}
