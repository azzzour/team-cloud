--liquibase formatted sql
--changeset author:Gizatulin Alik id:v1_5
--preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'shared_resources';


CREATE TABLE shared_resources(
    id BIGSERIAL PRIMARY KEY,
    resource_type VARCHAR(20) NOT NULL CHECK (resource_type IN ('FILE', 'FOLDER')),
    resource_id UUID NOT NULL,
    shared_by UUID NOT NULL,
    shared_with UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unique_shared_resource UNIQUE(resource_id,shared_by,shared_with)
 )

--rollback DROP TABLE shared_resources;