package com.example.mobile_money_integration_system.contoller;
import com.example.mobile_money_integration_system.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import com .example.mobile_money_integration_system.entity.User;
import com.example.mobile_money_integration_system.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final TransactionService transactionService;

    public UserController(UserRepository userRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @GetMapping("/balance/{username}")
    public Double getBalance(@PathVariable String username) {
        return transactionService.getBalance(username);
    }
}