--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_1
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'teams_storage';

CREATE TABLE teams_storage (
    team_id UUID PRIMARY KEY,
    total_size BIGINT NOT NULL DEFAULT 10000000000 CHECK(total_size BETWEEN 1000000000 AND 1000000000000),
    reserved_size BIGINT NOT NULL DEFAULT 0 CHECK ( reserved_size BETWEEN 0 AND total_size),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
)

--rollback DROP TABLE teams_storage;