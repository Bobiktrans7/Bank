package com.example.Bank.Controler;
import com.example.Bank.Entity.LoginRequest;
import com.example.Bank.Entity.Stuff;
import com.example.Bank.Interface.StuffRepository;
import com.example.Bank.Interface.StuffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


    @RestController
    @RequestMapping("/api/auth")
    public class AuthController{
        private final StuffService clientService;
        private final StuffRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public AuthController(StuffService clientService, StuffRepository userRepository, PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager) {
            this.clientService = clientService;
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
            this.authenticationManager = authenticationManager;
        }

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody Stuff user) {
            try {
                clientService.registerStuff(user);
                return ResponseEntity.ok("Client registered successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering client");
            }
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok().build();
        }
        -- Создание таблицы для складов
        CREATE TABLE warehouses (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                number INT NOT NULL
        );

-- Создание таблицы для стеллажей
        CREATE TABLE racks (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                number INT NOT NULL,
                warehouse_id BIGINT NOT NULL,
                FOREIGN KEY (warehouse_id) REFERENCES warehouses(id)
                );

-- Создание таблицы для ячеек
        CREATE TABLE cells (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                number INT NOT NULL,
                rack_id BIGINT NOT NULL,
                FOREIGN KEY (rack_id) REFERENCES racks(id)
                );
    }

