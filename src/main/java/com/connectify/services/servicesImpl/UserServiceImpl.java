package com.connectify.services.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.connectify.entities.User;
import com.connectify.forms.Userform;
import com.connectify.helper.AppConstants;
import com.connectify.helper.UserNotFoundException;
import com.connectify.repositories.UserRepo;
import com.connectify.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        repo.save(user);
    }

    @Override
    public void updateUser(User user) throws UserNotFoundException{
        User existingUser = repo.findById(user.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found with id: " + user.getUserId()) );
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAbout(user.getAbout());
        existingUser.setProfilePic(user.getProfilePic());
        existingUser.setEmailVerified(user.isEmailVerified());
        existingUser.setEnabled(user.isEnabled());
        existingUser.setProvider(user.getProvider());
        existingUser.setProviderUserId(user.getProviderUserId());
        existingUser.setContacts(user.getContacts());
    }

    @Override
    public void deleteUser(String id) {
        User existingUser = repo.findById(id).orElseThrow(() -> new UserNotFoundException("user not found with id: " + id) );
        repo.delete(existingUser);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return repo.findById(id);
    }

    @Override
    public boolean isUserExists(String id) {
        Optional<User> existingUser = repo.findById(id);
        if(existingUser.isPresent()) return true;
        return false;
    }

    @Override
    public boolean isUserExistsByEmail(String emailId) {
        Optional<User> existingUser = repo.findByEmail(emailId);
        if(existingUser.isPresent()) return true;
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public void registerNewUser(Userform userform){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(userform.getPassword()));
        user.setName(userform.getName());
        user.setEmail(userform.getEmail());
        user.setPhoneNumber(userform.getPhoneNumber());
        user.setAbout(userform.getAbout());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        repo.save(user);
    }

}
