package com.alikgizatulin.teamapp.repository;

import com.alikgizatulin.teamapp.entity.Team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID>, JpaSpecificationExecutor<Team> {

    @Query("""
                SELECT t FROM Team t JOIN t.teamMembers tm
                WHERE
                    (tm.userId = :userId)
                    AND (:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%',:name, '%')))
            """)
    Page<Team> findAllUserTeams(@Param("userId") String userId,
                                             @Param("name") String name,
                                             Pageable pageable);

    Page<Team> findAllByOwnerIdAndNameContainsIgnoreCase(String ownerId, String name, Pageable pageable);
    boolean existsByOwnerIdAndName(String ownerId, String name);

}
