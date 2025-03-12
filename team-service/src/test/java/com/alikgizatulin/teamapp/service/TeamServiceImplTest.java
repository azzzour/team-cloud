package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.exception.TeamNotFoundException;
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

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    @DisplayName("Should return Team when it exists")
    void getById_whenExists_returnTeamEntity() {
        //given
        when(this.teamRepository.findById(teamId))
                .thenReturn(Optional.of(TeamUtils.getTestTeamPersisted(teamId)));

        //when
        Team team = this.teamService.getTeamById(teamId);

        //then
        assertNotNull(team);
        assertEquals(team.getId(), teamId);

        verify(this.teamRepository).findById(teamId);
    }

    @Test
    @DisplayName("Should throw TeamNotFoundException when team does not exist")
    void getById_whenNotExists_throwTeamNotFoundException() {
        //given
        when(this.teamRepository.findById(teamId))
                .thenReturn(Optional.empty());

        //when & then
        assertThrows(
                TeamNotFoundException.class, () ->
                        this.teamService.getTeamById(teamId)
        );

        verify(this.teamRepository).findById(teamId);
    }

    @Test
    @DisplayName("Should create and return a saved Team when it does not exist")
    void create_whenNotExists_returnSavedTeam() {
        //given
        Team teamToSave = TeamUtils.getTestTeamTransient();
        when(this.teamRepository.existsByOwnerIdAndName(teamToSave.getOwnerId(),teamToSave.getName()))
                .thenReturn(Boolean.FALSE);
        when(this.teamRepository.saveAndFlush(teamToSave))
                .thenReturn(TeamUtils.getTestTeamPersisted(teamId));

        //when
        Team savedTeam = this.teamService.create(teamToSave);

        //then
        assertNotNull(savedTeam);
        assertEquals(savedTeam.getId(),teamId);
        assertEquals(savedTeam.getName(),teamToSave.getName());
        assertEquals(savedTeam.getOwnerId(),teamToSave.getOwnerId());
        verify(this.teamRepository,times(1)).saveAndFlush(teamToSave);
        verify(this.teamRepository,times(1))
                .existsByOwnerIdAndName(teamToSave.getOwnerId(),teamToSave.getName());
    }

    @Test
    @DisplayName("Should throw TeamWithDuplicateNameException when trying to create a team with a duplicate name")
    void create_whenExists_throwTeamWithDuplicateNameException() {
        //given
        Team teamToSave = TeamUtils.getTestTeamTransient();
        when(this.teamRepository.existsByOwnerIdAndName(teamToSave.getOwnerId(),teamToSave.getName()))
                .thenReturn(Boolean.TRUE);

        //when & then
        assertThrows(
                TeamWithDuplicateNameException.class, () ->
                        this.teamService.create(teamToSave)
        );
        verify(this.teamRepository,never()).saveAndFlush(teamToSave);
        verify(this.teamRepository,times(1))
                .existsByOwnerIdAndName(teamToSave.getOwnerId(),teamToSave.getName());
    }

}
