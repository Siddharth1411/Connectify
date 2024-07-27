package com.connectify.services;

import java.util.List;
import java.util.Optional;

import com.connectify.entities.User;
import com.connectify.forms.Userform;

public interface UserService {

    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(String id);
    Optional<User> getUserById(String id);
    boolean isUserExists(String id);
    boolean isUserExistsByEmail(String emailId);
    List<User> getAllUsers();
    void registerNewUser(Userform userform);
 }
