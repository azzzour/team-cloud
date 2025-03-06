--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_6
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'role_relations';



CREATE TABLE roles_relations(
    id BIGSERIAL PRIMARY KEY,
    team_id UUID NOT NULL REFERENCES teams(id) ON DELETE CASCADE ,
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    target_role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,

    --visibility of users and files
    can_view_members BOOLEAN NOT NULL DEFAULT FALSE,
    can_view_files BOOLEAN NOT NULL DEFAULT FALSE,
    can_read_files BOOLEAN NOT NULL DEFAULT FALSE,


    --file operations
    can_edit_files BOOLEAN NOT NULL DEFAULT FALSE,
    can_upload_files BOOLEAN NOT NULL DEFAULT FALSE,
    can_delete_files BOOLEAN NOT NULL DEFAULT FALSE,
    can_download_files BOOLEAN NOT NULL DEFAULT FALSE,

    -- Logical constraints
    CHECK (can_view_files <= can_view_members), -- can't read if you can't see the file
    CHECK (can_read_files <= can_view_files), -- can't read if you can't see the file
    CHECK (can_edit_files <= can_read_files), -- can't edit if you can't read
    CHECK (can_delete_files <= can_edit_files), -- can't delete if you can't edit
    CHECK (can_download_files <= can_read_files), -- can't download if you can't read
    CHECK (can_upload_files <= can_view_files), -- can't upload if you can't see the files

    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_role_relation UNIQUE (role_id,target_role_id)
);

--rollback DROP TABLE role_relations;