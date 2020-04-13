package com.thongkao.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Invalid username or password");
    }
}
