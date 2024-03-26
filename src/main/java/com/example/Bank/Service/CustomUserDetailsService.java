package com.example.Bank.Service;

import com.example.Bank.Entity.Client;
import com.example.Bank.Interface.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {
    private ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client clinet = clientRepository.findClientBy(username);
        if (username == null) {
            throw new UsernameNotFoundException("No user found with email");
        }
        List<String> roles = Arrays.asList(clinet.getRole());
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(clinet.getUsername())
                        .password(clinet.getPassword())
                        .roles(clinet.getRole())
                        .build();

        return userDetails;
    }
}
