package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatOrderByDateEnvoi(Long chatId);

}
