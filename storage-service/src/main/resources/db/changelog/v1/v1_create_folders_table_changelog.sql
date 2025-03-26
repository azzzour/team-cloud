--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_3
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'folders';

CREATE TABLE folders(
    id UUID PRIMARY KEY,
    member_id UUID NOT NULL,
    parent_folder_id UUID REFERENCES folders(id) ON DELETE CASCADE,
    name varchar(50) NOT NULL CHECK (LENGTH(name) BETWEEN 1 AND 50),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_folder_name UNIQUE(member_id,parent_folder_id,name)
)

--rollback DROP TABLE folders;