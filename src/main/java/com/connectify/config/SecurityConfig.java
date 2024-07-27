package com.connectify.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.connectify.services.servicesImpl.SecurityCustomUserDetailsService;


@Configuration
public class SecurityConfig {
    //hardcoded users stored in memory not in db
    //create user and login using java code in memory service
    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails user1 = User.withDefaultPasswordEncoder().username("ciddy").password("ciddy").roles("Admin", "User").build();
    //     UserDetails user2 = User.withDefaultPasswordEncoder().username("kiddy").password("kiddy").roles("Admin", "User").build();
    //     return new InMemoryUserDetailsManager(user1, user2);
    // }
    @Autowired
    private SecurityCustomUserDetailsService userDetailsService;

    @Autowired
    private OAuthAuthenticationHandler handler;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //userDetailsService object to be passed
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //passwordEncoder object to be passed
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //configure url access
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        //login form access restored
        // httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
            // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        //oauth configurations

        // httpSecurity.oauth2Login(Customizer.withDefaults());
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}

