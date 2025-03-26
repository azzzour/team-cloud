--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_2
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'members_storage';


CREATE TABLE members_storage (
    member_id UUID PRIMARY KEY,
    team_storage_id UUID NOT NULL REFERENCES teams_storage(team_id) ON DELETE CASCADE ,
    total_size BIGINT NOT NULL DEFAULT 10000000000 CHECK(total_size BETWEEN 10000000 AND 1000000000000),
    used_size BIGINT NOT NULL DEFAULT 0 CHECK ( used_size BETWEEN 0 AND total_size),
    is_locked boolean NOT NULL DEFAULT false,
    updated_at TIMESTAMP NOT NULL DEFAULT now()
)

--rollback DROP TABLE members_storage;