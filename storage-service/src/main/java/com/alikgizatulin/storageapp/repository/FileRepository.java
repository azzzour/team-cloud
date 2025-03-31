package com.alikgizatulin.storageapp.repository;

import com.alikgizatulin.storageapp.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
}
