package com.alikgizatulin.teamapp.repository;

import com.alikgizatulin.teamapp.entity.TeamSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamSettingsRepository extends JpaRepository<TeamSettings, Long> {
}
