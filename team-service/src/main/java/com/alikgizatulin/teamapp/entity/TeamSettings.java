package com.alikgizatulin.teamapp.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "team_settings", uniqueConstraints = {
        @UniqueConstraint(name = "unique_default_role", columnNames = "default_role_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class TeamSettings {

    @Id
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "id")
    @NonNull
    private Team team;

    @Builder.Default
    private UUID defaultRoleId = null;

    @Builder.Default
    @NonNull
    @Column(nullable = false)
    private Boolean deleteFilesOnExit = false;

    @Builder.Default
    @NonNull
    @Column(nullable = false)
    private Long totalStorageLimit = 10000000000L;

    @Builder.Default
    @NonNull
    @Column(nullable = false)
    private Integer maxUsers = 10;

    @Builder.Default
    @NonNull
    @Column(nullable = false)
    private Long maxFileSize = 1000000000L;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;


}
