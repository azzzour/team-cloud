package com.alikgizatulin.teamapp.repository;

import com.alikgizatulin.teamapp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    List<Team> findAllByOwnerId(String ownerId);
 }
