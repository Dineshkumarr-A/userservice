package com.mycompany.userservice.controllers;

import com.mycompany.userservice.dtos.LogOutRequestDto;
import com.mycompany.userservice.dtos.LoginRequestDto;
import com.mycompany.userservice.dtos.SignUpRequestDto;
import com.mycompany.userservice.dtos.UserDto;
import com.mycompany.userservice.models.Token;
import com.mycompany.userservice.models.User;
import com.mycompany.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService _userService;

    public UserController(UserService userService)
    {
        _userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequestDto signUpRequestDto)
    {
        User user =  _userService.signUp(signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword(),signUpRequestDto.getName());

        return UserDto.from(user);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto)
    {
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogOutRequestDto logOutRequestDto)
    {
        return null;
    }
}
