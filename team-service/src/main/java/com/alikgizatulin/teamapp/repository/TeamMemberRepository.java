package com.alikgizatulin.teamapp.repository;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, UUID> {

    Page<TeamMember> findAllByTeam(Team team, Pageable pageable);

    Optional<TeamMember> findByUserIdAndTeam(String userId, Team team);

    @Query("SELECT tm FROM TeamMember tm WHERE tm.userId = :userId AND tm.team.id = :teamId")
    Optional<TeamMember> findByUserIdAndTeamId(@Param("userId") String userId, @Param("teamId") UUID teamId);

    @Query("SELECT tm FROM TeamMember tm JOIN FETCH tm.team WHERE tm.id = :id")
    Optional<TeamMember> findByIdWithTeam(@Param("id") UUID id);

    boolean existsByUserIdAndTeam(String userId, Team team);

    @Query("SELECT COUNT(tm) > 0 FROM TeamMember tm WHERE tm.userId = :userId AND tm.team.id = :teamId")
    boolean existsByUserIdAndTeamId(@Param("userId") String userId, @Param("teamId") UUID teamId);

    @Query("""
            SELECT COUNT(tm) > 0 FROM TeamMember tm
            WHERE tm.id = :memberId AND tm.team.ownerId = :userId
            """)
    boolean existsByMemberIdAndTeamOwnerId(@Param("memberId") UUID memberId, @Param("userId") String userId);


    @Query("""
            SELECT COUNT(tm2) > 0
            FROM TeamMember tm1
            JOIN TeamMember tm2 ON tm1.team.id = tm2.team.id
            WHERE tm1.id = :memberId AND tm2.userId = :userId
            """)
    boolean existsUserInSameTeamAsMember(@Param("memberId") UUID memberId, @Param("userId") String userId);


}
