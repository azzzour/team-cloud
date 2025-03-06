--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_3
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'roles';


CREATE TABLE roles (
    id UUID PRIMARY KEY,
    team_id UUID NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    name VARCHAR(20) NOT NULL CHECK(LENGTH(name) >= 3),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_team_role_name UNIQUE(team_id,name)
);

--rollback DROP TABLE roles;