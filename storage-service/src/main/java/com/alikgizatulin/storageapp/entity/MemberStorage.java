package com.alikgizatulin.storageapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "members_storage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "memberId")
@ToString(exclude = "teamStorage")
public class MemberStorage implements Persistable<UUID> {

    @Id
    private UUID memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_storage_id")
    private TeamStorage teamStorage;

    @Builder.Default
    @Column(nullable = false)
    private long totalSize = 10000000000L;

    @Builder.Default
    @Column(nullable = false)
    private long usedSize = 0;

    @Builder.Default
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @Override
    public UUID getId() {
        return this.getId();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
