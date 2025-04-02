--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_4
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'files';


CREATE TABLE files(
    id UUID PRIMARY KEY,
    member_storage_id UUID NOT NULL REFERENCES members_storage(member_id) ON DELETE CASCADE,
    folder_id UUID REFERENCES folders(id) ON DELETE CASCADE,
    name varchar(255) NOT NULL CHECK (LENGTH(name) >= 1),
    size BIGINT NOT NULL CHECK (size >= 0),
    content_type varchar(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT unique_file_name UNIQUE(member_storage_id,folder_id,name)
)

--rollback DROP TABLE files;