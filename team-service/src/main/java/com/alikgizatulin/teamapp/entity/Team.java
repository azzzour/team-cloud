package com.alikgizatulin.teamapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
@EqualsAndHashCode(of = {"name", "ownerId"})
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(nullable = false, length = 20)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String ownerId;

    @Builder.Default
    @OneToMany(mappedBy = "team",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private List<TeamMember> teamMembers = Collections.synchronizedList(new ArrayList<>());

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    public void addTeamMember(@NonNull TeamMember teamMember) {
        if (this.teamMembers.contains(teamMember)) {
            throw new IllegalArgumentException("The team with id: {%s} already contains this teamMember"
                    .formatted(this.id));
        }
        if (teamMember.getTeam() != null) {
            throw new IllegalArgumentException("TeamMember already has a team: {%s}".formatted(teamMember.getTeam()));
        }
        this.teamMembers.add(teamMember);
        teamMember.setTeam(this);
    }

    public List<TeamMember> getTeamMember() {
        return Collections.unmodifiableList(this.teamMembers);
    }

}
