package com.example.Bank.Controler;
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
    public class AuthController {
//        private final StuffService clientService;
//        private final StuffRepository userRepository;
//        private final PasswordEncoder passwordEncoder;
//        private final AuthenticationManager authenticationManager;
//
//        public AuthController(StuffService clientService, StuffRepository userRepository, PasswordEncoder passwordEncoder,
//                              AuthenticationManager authenticationManager) {
//            this.clientService = clientService;
//            this.userRepository = userRepository;
//            this.passwordEncoder = passwordEncoder;
//            this.authenticationManager = authenticationManager;
//        }
//
//        @PostMapping("/register")
//        public ResponseEntity<?> register(@RequestBody Stuff user) {
//            try {
//                clientService.registerStuff(user);
//                return ResponseEntity.ok("Client registered successfully");
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering client");
//            }
//        }
//
//        @PostMapping("/login")
//        public ResponseEntity<?> login(@RequestBody Stuff loginRequest) {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return ResponseEntity.ok().build();
//        }
    }

//    CREATE TABLE stuff(
//            id serial PRIMARY KEY ,
//            username varchar (100),
//    password varchar (500)
//);
//
//    CREATE TABLE shelves ( id SERIAL PRIMARY KEY, shelf_number INT NOT NULL UNIQUE );
//    CREATE TABLE cells ( id SERIAL PRIMARY KEY, shelf_id INT NOT NULL REFERENCES shelves(id), cell_number INT NOT NULL, UNIQUE (shelf_id, cell_number) );
//    CREATE TABLE orders ( id SERIAL PRIMARY KEY, order_number VARCHAR(255) NOT NULL UNIQUE, shelf_id INT NOT NULL REFERENCES shelves(id), cell_id INT NOT NULL REFERENCES cells(id), recipient_phone_number VARCHAR(20), start_date DATE NOT NULL, end_date DATE NOT NULL );
//    CREATE TABLE operation_log (
//            id SERIAL PRIMARY KEY,
//            order_id INT NOT NULL REFERENCES orders(id),
//    operation_type VARCHAR(10) NOT NULL,
//    operation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
//);



