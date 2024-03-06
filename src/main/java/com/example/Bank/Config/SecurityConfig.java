package com.example.Bank.Config;

import com.example.Bank.Entity.Client;
import com.example.Bank.Interface.ClientIntarface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ClientIntarface clientIntarface;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(login -> {
            Client client = clientIntarface.findByLogin(login);
            if (client != null) {
                return new org.springframework.security.core.userdetails.User(client.getLogin(), client.getPassword(), true, true, true, true, null);
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        }).passwordEncoder(new BCryptPasswordEncoder());
    }
}
