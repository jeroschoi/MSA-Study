package com.event.messageservice.example.repository;


import com.event.messageservice.example.entity.ExampleMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleMessageRepository extends JpaRepository<ExampleMessageEntity, Long> {
}
