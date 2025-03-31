package com.alikgizatulin.storageapp.repository;

import com.alikgizatulin.storageapp.entity.MemberStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberStorageRepository extends JpaRepository<MemberStorage, UUID> {
}
