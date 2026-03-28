package com.example.mobile_money_integration_system.repository;
import com.example.mobile_money_integration_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderUsernameOrReceiverUsername(String senderUsername, String receiverUsername);
}
