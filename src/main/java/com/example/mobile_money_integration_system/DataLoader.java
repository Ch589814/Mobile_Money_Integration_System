package com.example.mobile_money_integration_system;
import com.example.mobile_money_integration_system.entity.Transaction;
import com.example.mobile_money_integration_system.entity.User;
import com.example.mobile_money_integration_system.repository.TransactionRepository;
import com.example.mobile_money_integration_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(new User(
                        "System Admin",
                        "admin",
                        "admin@gmail.com",
                        passwordEncoder.encode("admin123"),
                        100000.0,
                        Set.of("ROLE_ADMIN")
                ));
            }

            if (userRepository.findByUsername("alice").isEmpty()) {
                userRepository.save(new User(
                        "Alice Uwase",
                        "alice",
                        "alice@gmail.com",
                        passwordEncoder.encode("alice123"),
                        50000.0,
                        Set.of("ROLE_USER")
                ));
            }

            if (userRepository.findByUsername("bob").isEmpty()) {
                userRepository.save(new User(
                        "Bob Iradukunda",
                        "bob",
                        "bob@gmail.com",
                        passwordEncoder.encode("bob123"),
                        30000.0,
                        Set.of("ROLE_USER")
                ));
            }

            if (transactionRepository.count() == 0) {
                transactionRepository.save(new Transaction(
                        "alice",
                        "bob",
                        10000.0,
                        "SEND",
                        LocalDateTime.now()
                ));
            }
        };
    }
}
