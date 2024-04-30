package com.mycompany.userservice.controllers;

import com.mycompany.userservice.dtos.*;
import com.mycompany.userservice.exceptions.InvalidPasswordException;
import com.mycompany.userservice.models.Token;
import com.mycompany.userservice.models.User;
import com.mycompany.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidPasswordException {
         Token token = _userService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());

         LoginResponseDto loginResponseDto = new LoginResponseDto();
         loginResponseDto.setToken(token);

        return loginResponseDto;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogOutRequestDto logOutRequestDto)
    {
        ResponseEntity<Void> responseEntity = null;
       try {
           _userService.logOut(logOutRequestDto.getToken());
           responseEntity = new ResponseEntity<>(HttpStatus.OK);
       }
       catch (Exception ex)
       {
           System.out.println("Something went wrong");
           responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
        return responseEntity;
    }
}
