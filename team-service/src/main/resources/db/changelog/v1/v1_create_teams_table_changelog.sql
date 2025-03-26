--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_1
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'teams';


CREATE TABLE teams (
    id UUID PRIMARY KEY,
    name VARCHAR(15) NOT NULL CHECK(LENGTH(name) >= 5),
    owner_id VARCHAR(255) NOT NULL,
    member_count INT NOT NULL DEFAULT 1 CHECK( member_count <= 100),
    status VARCHAR(10) NOT NULL DEFAULT 'PENDING'
                   CHECK (status in ('PENDING', 'ACTIVE','ERROR')),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_team_name UNIQUE(owner_id,name)
    );

--rollback DROP TABLE teams;