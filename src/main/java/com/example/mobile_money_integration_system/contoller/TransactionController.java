package com.example.mobile_money_integration_system.contoller;

import com.example.mobile_money_integration_system.dto.DepositWithdrawRequest;
import com.example.mobile_money_integration_system.dto.SendMoneyRequest;
import com.example.mobile_money_integration_system.dto.TransactionRequest;
import com.example.mobile_money_integration_system.entity.Transaction;
import com.example.mobile_money_integration_system.repository.TransactionRepository;
import com.example.mobile_money_integration_system.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/send")
    public Transaction sendMoney(@RequestBody SendMoneyRequest request) {
        return transactionService.sendMoney(
                request.getSenderUsername(),
                request.getReceiverUsername(),
                request.getAmount()
        );
    }

    @PostMapping("/deposit")
    public Transaction depositMoney(@RequestBody DepositWithdrawRequest request) {
        return transactionService.depositMoney(
                request.getUsername(),
                request.getAmount()
        );
    }

    @PostMapping("/withdraw")
    public Transaction withdrawMoney(@RequestBody DepositWithdrawRequest request) {
        return transactionService.withdrawMoney(
                request.getUsername(),
                request.getAmount()
        );
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/user/{username}")
    public List<Transaction> getUserTransactions(@PathVariable String username) {
        return transactionService.getUserTransactions(username);
    }
}