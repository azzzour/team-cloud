--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_1
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'teams';


CREATE TABLE teams (
    id UUID PRIMARY KEY,
    name varchar(20) NOT NULL CHECK(LENGTH(name) >= 5),
    owner_id varchar(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_team_name UNIQUE(owner_id,name)
    );

--rollback DROP TABLE teams;