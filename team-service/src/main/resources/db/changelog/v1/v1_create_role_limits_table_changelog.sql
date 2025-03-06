--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_4
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'role_limits';



CREATE TABLE role_limits (
    id BIGSERIAL PRIMARY KEY,
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    max_users INT NOT NULL DEFAULT 1 CHECK ( max_users BETWEEN 1 AND 999),
    current_users INT NOT NULL DEFAULT 0 CHECK ( current_users >= 0 ),
    total_storage_limit BIGINT NOT NULL DEFAULT 1000000000 CHECK(total_storage_limit BETWEEN 1 AND 999999999999),
    used_storage BIGINT NOT NULL DEFAULT 0 CHECK ( used_storage >= 0 ),
    max_files INT NOT NULL DEFAULT 100 CHECK ( max_files BETWEEN 0 AND 1000000),
    used_files INT NOT NULL DEFAULT 0 CHECK ( used_files >= 0 ),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_role_limit UNIQUE (role_id)
)

--rollback DROP TABLE role_limits;