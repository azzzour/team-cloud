package com.alikgizatulin.teamapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "team_members", uniqueConstraints = {
        @UniqueConstraint(name = "unique_team_member", columnNames = {"user_id", "team_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "team")
public class TeamMember{

    //equals and hash code
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamMemberStatus status = TeamMemberStatus.NO_STORAGE;

    @Builder.Default
    @Column(nullable = false,updatable = false)
    private Instant joinedAt = Instant.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TeamMember teamMember)) return false;
        return this.getId() != null && this.getId().equals(teamMember.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
