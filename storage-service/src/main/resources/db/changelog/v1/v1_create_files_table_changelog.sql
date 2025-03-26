--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_4
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'files';


CREATE TABLE files(
    id UUID PRIMARY KEY,
    member_id UUID NOT NULL,
    folder_id UUID REFERENCES folders(id) ON DELETE CASCADE,
    name varchar(50) NOT NULL CHECK (LENGTH(name) BETWEEN 1 AND 50),
    size BIGINT NOT NULL CHECK (size >= 0),
    content_type varchar(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_file_name UNIQUE(member_id,folder_id,name)
)

--rollback DROP TABLE files;