package com.alikgizatulin.userapp.exception;

import com.alikgizatulin.commonlibrary.exception.NotFoundException;

import static com.alikgizatulin.userapp.constant.ErrorCodes.USER_NOT_FOUND;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Object userId) {
        super(USER_NOT_FOUND, userId);
    }


    public UserNotFoundException(String message,String messageKey) {
       super(message,messageKey);
    }
}

