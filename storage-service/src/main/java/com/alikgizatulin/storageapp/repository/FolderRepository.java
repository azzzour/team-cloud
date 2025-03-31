package com.alikgizatulin.storageapp.repository;

import com.alikgizatulin.storageapp.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {

    boolean existsByNameAndParentFolder(String name, Folder parentFolder);


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
}
