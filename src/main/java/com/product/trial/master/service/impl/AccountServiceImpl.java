package com.product.trial.master.service.impl;

import com.product.trial.master.dto.UserDto;
import com.product.trial.master.entities.User;
import com.product.trial.master.repository.UserRepository;
import com.product.trial.master.service.AccountService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final UserRepository UserRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.mail}")
    private static String adminEmail;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(UserRepository UserRepository, PasswordEncoder passwordEncoder) {
        this.UserRepository = UserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(UserDto UserDto) {
        logger.info("--- ATTEMPTING TO ADD NEW USER: {} ---", UserDto.username());

        if (UserRepository.findUserByUsername(UserDto.username()) != null ) {
            logger.error("--- USER ALREADY EXISTS: {} ---", UserDto.username());
            throw new IllegalStateException("User already exists");
        }
        User User = map(UserDto);
        User.setPassword(passwordEncoder.encode(User.getPassword()));
        User savedUser = UserRepository.save(User);
        logger.info("--- User added successfully: {} ---", savedUser.getUsername());
        return savedUser;
    }

    @Override
    public User findUserByEmail(String email) {
        logger.info("--- FETCHING USER BY EMAIL: {} ---", email);
        return UserRepository.findUserByEmail(email);
    }

    private static User map(UserDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setFirstname(dto.firstname());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        if(dto.email().equals(adminEmail))
            user.setRole("ADMIN");
        else{
            user.setRole("USER");
        }
        return user;
    }

}

