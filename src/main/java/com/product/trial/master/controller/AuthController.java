package com.product.trial.master.controller;

import com.product.trial.master.configuration.JwtUtil;
import com.product.trial.master.constant.ApiEndpoints;
import com.product.trial.master.dto.AuthRequest;
import com.product.trial.master.dto.AuthResponse;
import com.product.trial.master.dto.UserDto;
import com.product.trial.master.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoints.API)
public class AuthController {

    private final AccountService accountService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AccountService accountService, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.accountService = accountService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(ApiEndpoints.ACCOUNT)
    public ResponseEntity<?> register(@RequestBody UserDto appUserDto) {
        accountService.addUser(appUserDto);
        return ResponseEntity.ok("Compte créé avec succès.");
    }

    @PostMapping(ApiEndpoints.TOKEN)
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String jwt = jwtUtil.generateToken(request.email());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
