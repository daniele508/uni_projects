package com.wsda.project.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.wsda.project.Repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private UserRepository userRepository;
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl(this.userRepository));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorization -> authorization
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/negoziante/**").hasAuthority("NEGOZIANTE")
                .requestMatchers("/user/**").hasAuthority("USER")
                .requestMatchers("/login").permitAll()
                .requestMatchers("/register").permitAll()
            )
            .formLogin( form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(new SimpleUrlAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
                )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );
        
        return http.build();
    }
}