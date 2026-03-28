package com.example.mobile_money_integration_system.service;
import com.example.mobile_money_integration_system.entity.Transaction;
import com.example.mobile_money_integration_system.entity.User;
import com.example.mobile_money_integration_system.repository.TransactionRepository;
import com.example.mobile_money_integration_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction sendMoney(String senderUsername, String receiverUsername, Double amount) {
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setSenderUsername(senderUsername);
        transaction.setReceiverUsername(receiverUsername);
        transaction.setAmount(amount);
        transaction.setType("SEND");
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public Transaction depositMoney(String username, Double amount) {
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setSenderUsername("SYSTEM");
        transaction.setReceiverUsername(username);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public Transaction withdrawMoney(String username, Double amount) {
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setSenderUsername(username);
        transaction.setReceiverUsername("SYSTEM");
        transaction.setAmount(amount);
        transaction.setType("WITHDRAW");
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public Double getBalance(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBalance();
    }

    public List<Transaction> getUserTransactions(String username) {
        return transactionRepository.findBySenderUsernameOrReceiverUsername(username, username);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
