package com.connectify.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.connectify.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UserRepo repo;
    Logger logger = org.slf4j.LoggerFactory.getLogger(OAuthAuthenticationHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("oAuthAuthenticationSuccessHandler");
        

        /* 
        DefaultOAuth2User user1 = (DefaultOAuth2User)authentication.getPrincipal();

        // user.getAttributes().forEach((key, value) -> {
        //     logger.info("{} : {}", key, value);
        // });

        //fetch data from google 
        String email = user1.getAttribute("email").toString();
        String name = user1.getAttribute("name").toString();
        String picture = user1.getAttribute("picture").toString();

        // logger.info(email);
        // logger.info(name);
        // logger.info(picture);


        //create user with fetched data
        User user2 = new User();
        user2.setEmail(email);
        user2.setName(name);
        user2.setProfilePic(picture);
        user2.setPassword("testPassword");
        user2.setUserId(UUID.randomUUID().toString());
        user2.setAbout("this user was saved through google login");
        user2.setProvider(Providers.GOOGLE);
        user2.setEnabled(true);
        user2.setEmailVerified(true);
        user2.setProviderUserId(user1.getName());
        user2.setRoleList(List.of(AppConstants.ROLE_USER));

        User user_in_db = repo.findByEmail(email).orElse(null);
        if(user_in_db == null){
            repo.save(user2);
            logger.info("user saved:" + email);
        }
            */

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
    
}
