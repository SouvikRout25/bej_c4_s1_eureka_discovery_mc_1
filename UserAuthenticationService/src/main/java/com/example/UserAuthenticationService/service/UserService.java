package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.exception.UserNotFoundException;

public interface UserService {
    User addUser(User user) throws UserAlreadyExistsException;
    User findByUserIdAndPassword(String userId,String password) throws UserNotFoundException;
}
