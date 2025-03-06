--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_9
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'team_members';


CREATE TABLE team_members (
    id BIGSERIAL PRIMARY KEY,
    team_id UUID NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    user_id varchar(100) NOT NULL,
    role_id UUID NOT NULL,
    joined_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    status VARCHAR(10) NOT NULL DEFAULT 'PENDING' CHECK(LENGTH(status) >= 3),
    CONSTRAINT unique_team_member UNIQUE (team_id,user_id)
);

--rollback DROP TABLE team_members;