package com.example.MyShowConductor_System.Config;


import com.example.MyShowConductor_System.security.JwtAuthenticationEntryPoint;
import com.example.MyShowConductor_System.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)//for speciific request based authentication
public class SecurityConfiguration {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
////        httpSecurity.authorizeHttpRequests((authz)->authz.anyRequest().authenticated()).httpBasic(withDefaults());
//
//        httpSecurity.csrf().disable()
//                .authorizeHttpRequests()
////                .antMatchers("/movies")
////                .permitAll()
//                .anyRequest()
//                .permitAll()
////                .authenticated()
//                .and()
//                .formLogin();
//        return httpSecurity.build();
//    }

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Configure security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
//                .cors().and()
                .authorizeHttpRequests(auth -> auth.antMatchers("/home/**").authenticated()
                        .antMatchers("/auth/login").permitAll()
                        .antMatchers("/auth/SignUp").permitAll().anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configure DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    // Get the AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // Use BCryptPasswordEncoder for password hashing
//        return new BCryptPasswordEncoder();
//    }
}
