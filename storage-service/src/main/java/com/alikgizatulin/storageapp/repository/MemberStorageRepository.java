package com.alikgizatulin.storageapp.repository;

import com.alikgizatulin.storageapp.entity.MemberStorage;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MemberStorageRepository extends JpaRepository<MemberStorage, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ms FROM MemberStorage ms WHERE ms.memberId = :memberId")
    Optional<MemberStorage> findByIdAndLock(@Param("memberId") UUID memberId);
}
