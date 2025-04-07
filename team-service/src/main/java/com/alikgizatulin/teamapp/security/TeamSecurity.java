package com.alikgizatulin.teamapp.security;

import com.alikgizatulin.teamapp.repository.TeamMemberRepository;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component()
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamSecurity {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    public boolean isMember(UUID teamId, String userId) {
        return this.teamMemberRepository.existsByUserIdAndTeamId(userId,teamId);
    }

    public boolean isOwner(UUID teamId, String userId) {
        return this.teamRepository.existsByIdAndOwnerId(teamId,userId);
    }

    public boolean isOwnerOfMemberTeam(UUID memberId, String userId){
        return this.teamMemberRepository.existsByMemberIdAndTeamOwnerId(memberId,userId);
    }

    public boolean isSameTeamMember(UUID memberId, String userId) {
        return this.teamMemberRepository.existsUserInSameTeamAsMember(memberId,userId);
    }

}
