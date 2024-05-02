package com.mycompany.userservice.Interface;

import com.mycompany.userservice.exceptions.InvalidPasswordException;
import com.mycompany.userservice.exceptions.InvalidTokenException;
import com.mycompany.userservice.models.Token;
import com.mycompany.userservice.models.User;

public interface IUserService {
    public User signUp(String email, String password, String name);
    public Token login(String email, String password) throws InvalidPasswordException;
    public void logOut(String tokenValue) throws InvalidTokenException;
    User validateToken(String token) throws InvalidTokenException;
}
