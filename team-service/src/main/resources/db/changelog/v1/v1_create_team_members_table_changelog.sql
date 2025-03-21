--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_2
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'team_members';


CREATE TABLE team_members (
    id UUID PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    team_id UUID NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    status VARCHAR(15) NOT NULL DEFAULT 'NO_STORAGE',
    joined_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_team_member UNIQUE(user_id,team_id)
);

--rollback DROP TABLE team_members;