package com.alikgizatulin.storageapp.repository;

import com.alikgizatulin.storageapp.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {

    boolean existsByMemberStorageIdAndNameAndParentFolder(UUID memberStorageId, String name, Folder parentFolder);


    @Query(value = """
    WITH RECURSIVE folder_path AS (
        SELECT id, folders.parent_folder_id, name, 1 AS depth
        FROM folders
        WHERE id = :folderId

        UNION ALL

        SELECT f.id, f.parent_folder_id, f.name, fp.depth + 1
        FROM folders f
        JOIN folder_path fp ON f.id = fp.parent_folder_id
    )
    SELECT STRING_AGG(name, '/' ORDER BY depth DESC) FROM folder_path;
    """, nativeQuery = true)
    String getPath(@RequestParam("folderId") UUID folderId);


    @Modifying
    @Query(value = """
        WITH RECURSIVE folder_hierarchy AS (
            SELECT id, parent_folder_id FROM folders WHERE id = :folderId
            UNION ALL
            SELECT f.id, f.parent_folder_id FROM folders f
            JOIN folder_hierarchy fh ON f.id = fh.parent_folder_id
        )
        UPDATE folders
        SET size = GREATEST(size + :size, 0), updated_at = now()
        WHERE id IN (SELECT id FROM folder_hierarchy);
    """, nativeQuery = true)
    void updateSize(@Param("folderId") UUID folderId, @Param("size") long size);
}
