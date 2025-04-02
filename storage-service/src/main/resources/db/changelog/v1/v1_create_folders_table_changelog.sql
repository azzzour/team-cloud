--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_3
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'folders';

CREATE TABLE folders(
    id UUID PRIMARY KEY,
    member_storage_id UUID NOT NULL REFERENCES members_storage(member_id) ON DELETE CASCADE,
    parent_folder_id UUID REFERENCES folders(id) ON DELETE CASCADE,
    name varchar(255) NOT NULL CHECK (LENGTH(name) >= 1),
    size BIGINT NOT NULL DEFAULT 0 CHECK ( size >= 0 ),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT unique_folder_name UNIQUE(member_storage_id,parent_folder_id,name)
)

--rollback DROP TABLE folders;