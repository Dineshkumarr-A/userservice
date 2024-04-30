package com.mycompany.userservice.Interface;

import com.mycompany.userservice.models.Token;
import com.mycompany.userservice.models.User;

public interface IUserService {
    public User signUp(String email, String password, String name);
    public Token login(String email, String password);
    public Void logOut(String email);
}
