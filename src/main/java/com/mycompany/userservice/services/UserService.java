package com.mycompany.userservice.services;

import com.mycompany.userservice.Interface.IUserService;
import com.mycompany.userservice.exceptions.InvalidPasswordException;
import com.mycompany.userservice.exceptions.InvalidTokenException;
import com.mycompany.userservice.models.Token;
import com.mycompany.userservice.models.User;

import com.mycompany.userservice.Interface.IUserService;
import com.mycompany.userservice.models.Token;
import com.mycompany.userservice.models.User;
import com.mycompany.userservice.repositories.TokenRepository;
import com.mycompany.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository _userRepository;
    private BCryptPasswordEncoder _bCryptPasswordEncoder;
    private TokenRepository _tokenRepository;

    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,TokenRepository tokenRepository)
    {
        _userRepository = userRepository;
        _bCryptPasswordEncoder = bCryptPasswordEncoder;
        _tokenRepository = tokenRepository;
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
    public Token login(String email, String password) throws InvalidPasswordException {

        Optional<User> optionalUser = _userRepository.findByEmail(email);

        if(optionalUser.isEmpty())
        {
            //User with given email is not present in the DB
            return null;
        }

        User user = optionalUser.get();

        if(!_bCryptPasswordEncoder.matches(password, user.getHashedPassword()))
        {
            //throw exception
            throw new InvalidPasswordException("Please enter correct password");
        }

        //If password matches then generate token
        Token token = generateToken(user);

        return _tokenRepository.save(token);
    }

    private Token generateToken(User user)
    {
        LocalDate currentTime = LocalDate.now(); // current time.
        LocalDate thirtyDaysFromCurrentTime = currentTime.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysFromCurrentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiryAt(expiryDate);

        //Token value is a randomly generated String of 128 characters.
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);

        return token;
    }

    @Override
    public void logOut(String tokenValue) throws InvalidTokenException {

        //validate the token is valid
        Optional<Token> optionalToken = _tokenRepository.findByValueAndDeleted(tokenValue, false);

        if(optionalToken.isEmpty())
        {
            //throw an exception
            throw new InvalidTokenException("Invalid Token");
        }

        Token token = optionalToken.get();
        token.setDeleted(true);

        _tokenRepository.save(token);

        return;
    }
}