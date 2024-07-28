package com.connectify.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.connectify.entities.Providers;
import com.connectify.entities.User;
import com.connectify.helper.AppConstants;
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
        
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;

        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();

        // oauthUser.getAttributes().forEach((key, value) -> {
        //     logger.info(key + ":" + value);
        // });

        User user = new User();
        user.setPassword("testPassword");
        user.setUserId(UUID.randomUUID().toString());
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
            String email = oauthUser.getAttribute("email").toString();
            String name = oauthUser.getAttribute("name").toString();
            String picture = oauthUser.getAttribute("picture").toString();
            String providerUserId = oauthUser.getName();

            user.setName(name);
            user.setEmail(email);
            user.setEmailVerified(true);
            user.setProfilePic(picture);
            user.setAbout("signed in with google");
            user.setProvider(Providers.GOOGLE);
            user.setProviderUserId(providerUserId);
        }
        else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
            String email = oauthUser.getAttribute("email");
            if(email == null){
                email = oauthUser.getAttribute("login").toString() + "@gmail.com";
            }
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProvider(Providers.GITHUB);
            user.setProviderUserId(providerUserId);
            user.setAbout("signed in with github");
            
        }
        else if(authorizedClientRegistrationId.equalsIgnoreCase("linkedin")){

        }
        else{
            logger.info("OAuthAuthenticationSuccessHandler: Unknown provider");
        }

            User user_in_db = repo.findByEmail(user.getEmail()).orElse(null);
            if(user_in_db == null){
                repo.save(user);
                logger.info("user saved:" + user.getEmail());
            }    

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
    
}
