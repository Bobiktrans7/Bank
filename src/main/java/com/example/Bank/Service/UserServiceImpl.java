package com.example.Bank.Service;


import com.example.Bank.Entity.Stuff;
import com.example.Bank.Interface.StuffRepository;
import com.example.Bank.Interface.StuffService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements StuffService {
    private final StuffRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(StuffRepository userRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void registerStuff(Stuff client) {
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
        clientRepository.save(client);
    }


}
