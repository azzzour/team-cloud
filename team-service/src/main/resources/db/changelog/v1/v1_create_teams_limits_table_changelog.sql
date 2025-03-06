--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_2
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'teams_limits';


CREATE TABLE teams_limits (
    id BIGSERIAL PRIMARY KEY,
    used_storage BIGINT NOT NULL DEFAULT 0 CHECK(used_storage >= 0),
    user_count INT NOT NULL DEFAULT 1 CHECK(user_count >= 1),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    update_at TIMESTAMP NOT NULL DEFAULT now()
    );

--rollback DROP TABLE teams_limits;
