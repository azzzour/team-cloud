package com.alikgizatulin.storageapp.entity;

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
@Table(name = "folders",uniqueConstraints = {
        @UniqueConstraint(name = "unique_folder_name", columnNames = {"member_storage_id", "parent_folder_id","name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"parentFolder","childFolders","childFiles"})
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID memberStorageId;


    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parentFolder;

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "parentFolder")
    private List<Folder> childFolders = Collections.synchronizedList(new ArrayList<>());

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "folder")
    private List<File> childFiles = Collections.synchronizedList(new ArrayList<>());

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @Column(nullable = false)
    private long size = 0;

    @Builder.Default
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Folder folder)) return false;
        return this.getId() != null && this.getId().equals(folder.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addChildFolder(Folder folder) {
        this.childFolders.add(folder);
        folder.setParentFolder(this);
    }

    public void removeChildFolder(Folder folder) {
        if(this.childFolders.remove(folder)) {
            folder.setParentFolder(null);
        }
    }

    public void  addChildFile(File file) {
        this.childFiles.add(file);
        file.setFolder(this);
    }

    public void removeChildFile(File file) {
        if(this.childFiles.remove(file)) {
            file.setFolder(null);
        }
    }
}
