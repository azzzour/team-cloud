package com.alikgizatulin.storageapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

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
public class MemberStorage {

    @Id
    private UUID memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeamStorage teamStorage;

    @Builder.Default
    @Column(nullable = false)
    private long totalSize = 10000000000L;

    @Builder.Default
    @Column(nullable = false)
    private long usedSize = 0;

    @Builder.Default
    @Column(nullable = false)
    private boolean isLocked = false;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}
