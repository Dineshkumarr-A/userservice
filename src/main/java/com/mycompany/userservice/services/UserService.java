package com.mycompany.userservice.services;

import com.mycompany.userservice.Interface.IUserService;
import com.mycompany.userservice.models.Token;
import com.mycompany.userservice.models.User;

import com.mycompany.userservice.Interface.IUserService;
import com.mycompany.userservice.models.Token;
import com.mycompany.userservice.models.User;
import com.mycompany.userservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository _userRepository;
    private BCryptPasswordEncoder _bCryptPasswordEncoder;

    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        _userRepository = userRepository;
        _bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public User signUp(String email, String password, String name) {
        Optional<User> optionalUser = _userRepository.findByEmail(email);

        if(optionalUser.isPresent())
        {
            return optionalUser.get();
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(_bCryptPasswordEncoder.encode((password)));

        return _userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        return null;
    }

    @Override
    public Void logOut(String email) {
        return null;
    }
}