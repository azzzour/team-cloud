package com.alikgizatulin.teamapp.repository;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, UUID> {

    boolean existsByUserIdAndTeam(String userId, Team team);

    @Query("SELECT COUNT(tm) > 0 FROM TeamMember tm WHERE tm.userId = :userId AND tm.team.id = :teamId")
    boolean existsByUserIdAndTeamId(@Param("userId") String userId, @Param("teamId") UUID teamId);

    @Query("SELECT tm FROM TeamMember tm WHERE tm.userId = :userId AND tm.team.id = :teamId")
    Optional<TeamMember> findByUserIdAndTeamId(@Param("userId") String userId, @Param("teamId") UUID teamId);
}
