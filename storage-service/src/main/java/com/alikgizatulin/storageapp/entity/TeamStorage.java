package com.alikgizatulin.storageapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teams_storage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "membersStorage")
@EqualsAndHashCode(of = "teamId")
public class TeamStorage implements Persistable<UUID>{

    @Id
    private UUID teamId;

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "teamStorage",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    List<MemberStorage> membersStorage = Collections.synchronizedList(new ArrayList<>());

    @Builder.Default
    @Column(nullable = false)
    private long totalSize = 10000000000L;

    @Builder.Default
    @Column(nullable = false)
    private long reservedSize = 0;

    @Builder.Default
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;


    public List<MemberStorage> getMembersStorage() {
        return Collections.unmodifiableList(this.membersStorage);
    }

    public void addMemberStorage(MemberStorage memberStorage) {
        this.membersStorage.add(memberStorage);
        memberStorage.setTeamStorage(this);
    }

    public void removeMemberStorage(MemberStorage memberStorage) {
        if(this.membersStorage.remove(memberStorage)) {
            memberStorage.setTeamStorage(null);
        }
    }

    @Override
    public UUID getId() {
        return this.teamId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
