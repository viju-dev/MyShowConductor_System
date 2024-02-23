package com.example.MyShowConductor_System.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        // Used BCryptPasswordEncoder for password hashing
        return new BCryptPasswordEncoder();
    }

}
