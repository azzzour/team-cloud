--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_5
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'role_setting';

CREATE TABLE role_settings (
    id BIGSERIAL PRIMARY KEY,
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    can_share_files BOOLEAN NOT NULL DEFAULT FALSE,
    can_create_files BOOLEAN NOT NULL DEFAULT TRUE,
    can_view_team_members BOOLEAN NOT NULL DEFAULT TRUE,
    can_invite_users BOOLEAN NOT NULL DEFAULT FALSE,
    can_manage_team_settings BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_role_setting UNIQUE (role_id)
);

--rollback DROP TABLE role_settings;