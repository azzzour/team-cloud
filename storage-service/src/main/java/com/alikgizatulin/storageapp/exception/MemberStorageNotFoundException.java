package com.alikgizatulin.storageapp.exception;

import com.alikgizatulin.commonlibrary.exception.NotFoundException;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.MEMBER_STORAGE_NOT_FOUND;

public class MemberStorageNotFoundException extends NotFoundException {
    public MemberStorageNotFoundException(String message, String messageKey) {
        super(message, messageKey);
    }

    public MemberStorageNotFoundException(UUID id) {
        super(MEMBER_STORAGE_NOT_FOUND,new Object[]{id});
    }
}
