package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.dto.CreateTeamRequest;
import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.commonlibrary.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.exception.TeamWithDuplicateNameException;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.impl.TeamServiceImpl;
import com.alikgizatulin.teamapp.util.TeamUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest {

    private final UUID teamId = UUID.randomUUID();

    private final String ownerId = UUID.randomUUID().toString();

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    @DisplayName("Should return Team when it exists")
    void getById_whenTeamExists_returnTeamEntity() {
        //given
        when(this.teamRepository.findById(this.teamId))
                .thenReturn(Optional.of(TeamUtils.getTestTeamPersisted(this.teamId,this.ownerId)));

        //when
        Team team = this.teamService.getById(this.teamId);

        //then
        assertNotNull(team);
        assertEquals(team.getId(), this.teamId);

        verify(this.teamRepository).findById(this.teamId);
    }

    @Test
    @DisplayName("Should throw TeamNotFoundException when team does not exist")
    void getById_whenTeamNotExists_throwTeamNotFoundException() {
        //given
        when(this.teamRepository.findById(this.teamId))
                .thenReturn(Optional.empty());

        //when & then
        assertThrows(
                TeamNotFoundException.class, () ->
                        this.teamService.getById(this.teamId)
        );

        verify(this.teamRepository).findById(this.teamId);
    }

    @Test
    @DisplayName("Should create and return a saved Team when it does not exist")
    void create_whenTeamNotExists_returnSavedTeam() {
        //given
        Team teamToSave = TeamUtils.getTestTeamTransient(this.ownerId);
        var request = new CreateTeamRequest(teamToSave.getName(),
                1000000000L,10000000L);
        when(this.teamRepository.existsByOwnerIdAndName(teamToSave.getOwnerId(),teamToSave.getName()))
                .thenReturn(Boolean.FALSE);
        when(this.teamRepository.save(any()))
                .thenReturn(TeamUtils.getTestTeamPersisted(this.teamId,this.ownerId));
        //when
        Team savedTeam = this.teamService.create(this.ownerId,request);
        //then
        assertNotNull(savedTeam);
        assertEquals(savedTeam.getId(),this.teamId);
        assertEquals(savedTeam.getName(),teamToSave.getName());
        assertEquals(savedTeam.getOwnerId(),this.ownerId);
        verify(this.teamRepository,times(1)).save(any());
        verify(this.teamRepository,times(1))
                .existsByOwnerIdAndName(teamToSave.getOwnerId(),teamToSave.getName());
    }

    @Test
    @DisplayName("Should throw TeamWithDuplicateNameException when trying to create a team with a duplicate name")
    void create_whenTeamAlreadyExists_throwTeamWithDuplicateNameException() {
        //given
        Team teamToSave = TeamUtils.getTestTeamTransient(this.ownerId);
        var request = new CreateTeamRequest(teamToSave.getName(),
                1000000000L,10000000L);
        when(this.teamRepository.existsByOwnerIdAndName(teamToSave.getOwnerId(),teamToSave.getName()))
                .thenReturn(Boolean.TRUE);

        //when & then
        assertThrows(
                TeamWithDuplicateNameException.class, () ->
                        this.teamService.create(this.ownerId,request)
        );
        verify(this.teamRepository,never()).save(teamToSave);
        verify(this.teamRepository,times(1))
                .existsByOwnerIdAndName(teamToSave.getOwnerId(),teamToSave.getName());
    }

}
