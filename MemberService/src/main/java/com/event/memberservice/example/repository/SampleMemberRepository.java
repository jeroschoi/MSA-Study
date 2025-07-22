package com.event.memberservice.example.repository;


import com.event.memberservice.example.entity.SampleMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleMemberRepository extends JpaRepository<SampleMemberEntity, Long> {
}
