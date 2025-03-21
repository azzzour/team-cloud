package com.alikgizatulin.teamapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teams", uniqueConstraints = {
        @UniqueConstraint(name = "unique_team_name", columnNames = {"owner_id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "teamMembers")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @Column(nullable = false)
    private int memberCount = 1;

    @Column(nullable = false)
    private String ownerId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamStatus status = TeamStatus.PENDING;

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "team",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private List<TeamMember> teamMembers = Collections.synchronizedList(new ArrayList<>());

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Builder.Default
    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    public void addTeamMember(@NonNull TeamMember teamMember) {
        this.teamMembers.add(teamMember);
        teamMember.setTeam(this);
    }

   public void removeTeamMember(@NonNull TeamMember teamMember) {
       if (this.teamMembers.remove(teamMember)) {
           teamMember.setTeam(null);
       }
    }

    public List<TeamMember> getTeamMembers() {
        return Collections.unmodifiableList(this.teamMembers);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Team team)) return false;
        return this.getId() != null && this.getId().equals(team.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
