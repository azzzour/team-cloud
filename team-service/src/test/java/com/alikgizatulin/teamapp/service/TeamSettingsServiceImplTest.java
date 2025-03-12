package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamSettings;
import com.alikgizatulin.teamapp.exception.DuplicateTeamSettingsException;
import com.alikgizatulin.teamapp.exception.TeamSettingsNotFoundException;
import com.alikgizatulin.teamapp.repository.TeamSettingsRepository;
import com.alikgizatulin.teamapp.service.impl.TeamSettingsServiceImpl;
import com.alikgizatulin.teamapp.util.TeamSettingsUtils;
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
public class TeamSettingsServiceImplTest {

    private final UUID teamId = UUID.randomUUID();
    @Mock
    private TeamSettingsRepository teamSettingsRepository;

    @InjectMocks
    private TeamSettingsServiceImpl teamSettingsService;

    @Test
    @DisplayName("Should return TeamSettings when it exists")
    void getById_whenExists_returnTeamSettings() {
        //given
        Team team = TeamUtils.getTestTeamPersisted(teamId);
        TeamSettings teamSettings = TeamSettingsUtils.getTeamSettingsPersisted(team);
        when(this.teamSettingsRepository.findById(teamId)).thenReturn(Optional.of(teamSettings));

        //when
        TeamSettings returnedTeamSettings = this.teamSettingsService.getById(teamId);

        //then
        assertNotNull(returnedTeamSettings);
        assertEquals(returnedTeamSettings.getId(),teamId);

        verify(this.teamSettingsRepository).findById(teamId);
    }

    @Test
    @DisplayName("Should throw TeamSettingsNotFoundException when team settings does not exist")
    void getById_whenNotExists_throwTeamSettingsNotFoundException() {
        //given
        when(this.teamSettingsRepository.findById(teamId)).thenReturn(Optional.empty());

        //when & then
        assertThrows (
                TeamSettingsNotFoundException.class, () ->
                        this.teamSettingsService.getById(teamId)
        );

        verify(this.teamSettingsRepository).findById(teamId);
    }

    @Test
    @DisplayName("Should create and return saved TeamSettings when it does not exist")
    void create_whenNotExists_returnSavedTeamSettings() {
        //given
        Team savedTeam = TeamUtils.getTestTeamPersisted(teamId);
        TeamSettings teamSettingsToSave = TeamSettingsUtils.getTeamSettingsTransient(savedTeam);
        when(this.teamSettingsRepository.existsById(teamId))
                .thenReturn(Boolean.FALSE);
        when(this.teamSettingsRepository.save(teamSettingsToSave))
                .thenReturn(TeamSettingsUtils.getTeamSettingsPersisted(savedTeam));

        //when
        TeamSettings savedTeamSettings = this.teamSettingsService.create(teamSettingsToSave);

        //then
        assertNotNull(savedTeamSettings);
        assertEquals(savedTeamSettings.getId(),teamId);
        assertEquals(savedTeamSettings.getTeam(),savedTeam);
        verify(this.teamSettingsRepository,times(1)).existsById(teamId);
        verify(this.teamSettingsRepository,times(1)).save(teamSettingsToSave);
    }

    @Test
    @DisplayName("Should throw DuplicateTeamSettingsException when trying to create a duplicate team settings")
    void create_whenExists_throwDuplicateTeamSettingsException() {
        //given
        Team savedTeam = TeamUtils.getTestTeamPersisted(teamId);
        TeamSettings teamSettingsToSave = TeamSettingsUtils.getTeamSettingsTransient(savedTeam);
        when(this.teamSettingsRepository.existsById(teamId))
                .thenReturn(Boolean.TRUE);

        //when & then
        assertThrows(DuplicateTeamSettingsException.class,() ->
                this.teamSettingsService.create(teamSettingsToSave));
        verify(this.teamSettingsRepository,times(1)).existsById(teamId);
        verify(this.teamSettingsRepository,never()).save(teamSettingsToSave);
    }

}
