package com.alikgizatulin.teamapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "team_members", uniqueConstraints = {
        @UniqueConstraint(name = "unique_team_member", columnNames = {"team_id", "user_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "team")
@EqualsAndHashCode(of = {"team","userId"})
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false,updatable = false)
    private Team team;

    @NonNull
    @Column(nullable = false,updatable = false)
    private String userId;

    @NonNull
    @Column(nullable = false)
    private UUID roleId;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant joinedAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @NonNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status = MemberStatus.PENDING;


    public enum MemberStatus {
        PENDING,
        ACTIVE,
        REMOVED
    }
}
