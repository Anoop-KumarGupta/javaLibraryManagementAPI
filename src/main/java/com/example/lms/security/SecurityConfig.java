package com.example.lms.security;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {


    @Bean
    public UserDetailsManager userDetailsManager (DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
         http.authorizeHttpRequests(configurer->
                 configurer
                         .requestMatchers(HttpMethod.GET,"api/v1/books/**").hasRole("EMPLOYEE")
                         .requestMatchers(HttpMethod.POST,"api/v1/books").hasRole("EMPLOYEE")
                         .requestMatchers(HttpMethod.PUT,"api/v1/books/**").hasRole("EMPLOYEE")
                         .requestMatchers(HttpMethod.DELETE,"api/v1/books/**").hasRole("EMPLOYEE")

                         .requestMatchers(HttpMethod.GET,"api/v1/authors/**").hasRole("EMPLOYEE")
                         .requestMatchers(HttpMethod.POST,"api/v1/authors").hasRole("EMPLOYEE")
                         .requestMatchers(HttpMethod.PUT,"api/v1/authors/**").hasRole("EMPLOYEE")
                         .requestMatchers(HttpMethod.DELETE,"api/v1/authors/**").hasRole("EMPLOYEE")
          );
         http.httpBasic(Customizer.withDefaults());
         http.csrf(csrf->csrf.disable());
         return http.build();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails john= User.builder().username("Anoop")
//                .password("{noop}test123")
//                .roles("EMPLOYEE")
//                .build();
//        return new InMemoryUserDetailsManager(john);
//    }
}
