package com.mycompany.userservice.exceptions;

public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String message)
    {
        super(message);
    }
}
