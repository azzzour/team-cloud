--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_7
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'team_settings';


CREATE TABLE team_settings (
    id BIGSERIAL PRIMARY KEY,
    team_id UUID NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    default_role_id UUID REFERENCES roles(id) ON DELETE SET NULL DEFAULT NULL,
    delete_files_on_exit BOOLEAN NOT NULL DEFAULT FALSE,
    total_storage_limit BIGINT NOT NULL DEFAULT 10000000000 CHECK(total_storage_limit BETWEEN 1000000000 AND 1000000000000),
    max_users INT NOT NULL DEFAULT 10 CHECK(max_users BETWEEN 1 AND 1000),
    max_file_size BIGINT NOT NULL DEFAULT 1000000000 CHECK(max_file_size BETWEEN 10000000 AND 10000000000),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_default_role UNIQUE (default_role_id),
    CONSTRAINT unique_team_setting UNIQUE (team_id)
);

--rollback DROP TABLE team_settings;