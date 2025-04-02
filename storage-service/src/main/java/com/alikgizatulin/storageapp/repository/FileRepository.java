package com.alikgizatulin.storageapp.repository;

import com.alikgizatulin.storageapp.entity.File;
import com.alikgizatulin.storageapp.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    boolean existsByMemberStorageIdAndNameAndFolder(UUID memberStorageId, String name, Folder folder);

    @Query(value = """
    WITH RECURSIVE folder_path AS (
        SELECT id, parent_folder_id, name, 1 AS depth
        FROM folders
        WHERE id = (SELECT folder_id FROM files WHERE id = :fileId)

        UNION ALL

        SELECT f.id, f.parent_folder_id, f.name, fp.depth + 1
        FROM folders f
        JOIN folder_path fp ON f.id = fp.parent_folder_id
    )
    SELECT STRING_AGG(name, '/' ORDER BY depth DESC) || '/' || (SELECT name FROM files WHERE id = :fileId)
    FROM folder_path;
    """, nativeQuery = true)
    String getPath(@Param("fileId") UUID fileId);

}
