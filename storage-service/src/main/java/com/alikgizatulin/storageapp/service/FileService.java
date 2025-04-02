package com.alikgizatulin.storageapp.service;

import com.alikgizatulin.storageapp.dto.CreateFileRequest;

import java.util.UUID;

public interface FileService {

    void create(CreateFileRequest request);

    void deleteById(UUID id);
}
