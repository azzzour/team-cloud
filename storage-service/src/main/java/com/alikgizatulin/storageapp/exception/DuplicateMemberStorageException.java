package com.alikgizatulin.storageapp.exception;

import com.alikgizatulin.commonlibrary.exception.DuplicateException;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.DUPLICATE_MEMBER_STORAGE;

public class DuplicateMemberStorageException extends DuplicateException {
    public DuplicateMemberStorageException(String message, String messageKey) {
        super(message, messageKey);
    }

    public DuplicateMemberStorageException(UUID memberId) {
        super(DUPLICATE_MEMBER_STORAGE,new Object[]{memberId});
    }
}
